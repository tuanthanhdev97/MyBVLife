package app.com.baoviet.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import app.com.baoviet.R;
import app.com.baoviet.adapter.CustomDetailInforPaymentAdapter;
import app.com.baoviet.adapter.CustomExpandableListPaymentAdapter;
import app.com.baoviet.adapter.CustomExpandableListPaymentInforDetailAdapter;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.entity.Invoice;
import app.com.baoviet.entity.InvoiceDTO;
import app.com.baoviet.entity.KeyValueDTO;
import app.com.baoviet.entity.PremiumTransDTO;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.implement.GenericTextWatcher;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.CalculateDimension;
import app.com.baoviet.utility.StringUtil;
import app.com.baoviet.utility.Validate;

public class PaymentFragment extends Fragment implements View.OnClickListener, AsyncResponse, DialogEventListener, CompoundButton.OnCheckedChangeListener {
    private EditText edtPaymentContractNumber;
    private EditText edtPaymentBuyer;
    private EditText edtPaymentMobileNumber;
    private TextView tvPaymentContractNumberError;
    private TextView tvPaymentBuyerError;
    private TextView tvPaymentMobileNumberError;
    private TextView tvPaymentDetailTotal;
    private TextView tvPaymentDetailTypeDevice;
    private EditText edtPaymentDetailFullName;
    private EditText edtPaymentDetailTotalUnpaid;
    private LinearLayout lnPaymentDetailHeader;
    private ExpandableListView exListPaymentDetailInfor;
    private RadioGroup rdgPaymentDetailChooseMethod;
    private RadioButton rdPaymentDetailNapas;
    private RadioButton rdPaymentDetailBankBaoViet;
    private Spinner spPaymentContractNumber;
    private CheckBox cbPaymentTypeInput;
    private Button btnPayment;
    private ListView listviewPaymentDetail;
    private LinearLayout rlPaymentInforDetail;
    private CustomDetailInforPaymentAdapter mDetailInforPaymentAdapter;
    private CustomExpandableListPaymentInforDetailAdapter mExpandableListPaymentInforDetailAdapter;

    private List<PremiumTransDTO> listPreminumTrans = new ArrayList<PremiumTransDTO>();
    private CustomExpandableListPaymentAdapter mExpandableListPaymentAdapter;
    public static List<Invoice> mListInvoiceChoose;
    public static SortedSet<Integer> listPositionChecked = new TreeSet<>();
//    public static List<Integer> listNumberSelected;

    private Button btnPaymentSearch;
    private String fullName = Constant.EMPTY;
    private String phoneNumber = Constant.EMPTY;
    private List<String> listAccount = new ArrayList<String>();
    private DataSourceConnection connection;
    private String accountNumber;
    private String urlReturn;
    private String linkNapas;
    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        // check login or not to show fullname
        if (!StringUtil.isNullOrEmpty(Constant.TOKEN)) {
            phoneNumber = StringUtil.setTextValue(getArguments().getString(Keys.KEY_PHONE_NUMBER));
            fullName = StringUtil.setTextValue(getArguments().getString(Keys.KEY_FULLNAME));
            listAccount = getArguments().getStringArrayList(Keys.KEY_LIST_ACCOUNT_NUMBER);
            if (listAccount != null) {
                setAccountNumber(listAccount.get(Constant.INT_0));
            }
        } else {
            fullName = Constant.EMPTY;
            phoneNumber = Constant.EMPTY;
        }
        // initial view
        initInterface(view);

        //initial List Choose from checkbox
        mListInvoiceChoose = new ArrayList<Invoice>();
//        listNumberSelected = new ArrayList<Integer>();

        // event onchange data in edittext
        onChangeValueEditText();
        // event click button send infor
        btnPaymentSearch.setOnClickListener(this);
        btnPayment.setOnClickListener(this);

        return view;
    }

    public void searchInvoice() {
        try {
            JSONObject postDataParams = new JSONObject();
            String contractNumber = Constant.EMPTY;
            if (StringUtil.isNullOrEmpty(Constant.TOKEN)) {
                contractNumber = edtPaymentContractNumber.getText().toString();
            } else {
                if (!cbPaymentTypeInput.isChecked()) {
                    contractNumber = getAccountNumber();
                } else {
                    contractNumber = edtPaymentContractNumber.getText().toString();
                }
            }
            postDataParams.put(Keys.KEY_NUMBER_INVOICE, contractNumber);
            postDataParams.put(Keys.KEY_NAME_PAYER, edtPaymentBuyer.getText().toString());
            postDataParams.put(Keys.KEY_PHONE_PAYER, edtPaymentMobileNumber.getText().toString());
            postDataParams.put(Keys.KEY_LOGIN, StringUtil.isNullOrEmpty(Constant.TOKEN) ? false : true);
            String inputParams = postDataParams.toString().replace("\\", "").replace("\"[", "[").replace("]\"", "]");
            Activity activity = getActivity();
            if (isAdded() && activity != null) {
                connection = new DataSourceConnection(activity, Constant.URL_SEARCH_INVOICE, inputParams, Constant.METHOD_POST, true, true);
                connection.delegate = this;
                connection.execute();
            }

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            message = getResources().getString(R.string.message_error_system);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
        }
    }

    public void paymentBillNapas() {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            String json = gson.toJson(mListInvoiceChoose);
            JSONObject postDataParams = new JSONObject();
            postDataParams.put(Keys.KEY_POLICY_HOLDER, edtPaymentBuyer.getText().toString());
            postDataParams.put(Keys.KEY_PHONE_NUMBER, edtPaymentMobileNumber.getText().toString());
            postDataParams.put(Keys.KEY_LOGIN, StringUtil.isNullOrEmpty(Constant.TOKEN) ? false : true);
            postDataParams.put(Keys.KEY_INVOICE_LIST, json);
            String inputParams = postDataParams.toString().replace("\\", "").replace("\"[", "[").replace("]\"", "]");
            Activity activity = getActivity();
            if (isAdded() && activity != null) {
                connection = new DataSourceConnection(activity, Constant.URL_PAYMENT_NAPAS, inputParams, Constant.METHOD_POST, true, true);
                connection.delegate = this;
                connection.execute();
            }

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            message = getResources().getString(R.string.message_error_system);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
        }
    }

    public void paymentBillBaoVietBank() {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            String params = gson.toJson(mListInvoiceChoose);
            Activity activity = getActivity();
            if (isAdded() && activity != null) {
                connection = new DataSourceConnection(activity, Constant.URL_PAYMENT_BAOVIET_BANK, params, Constant.METHOD_POST, true, true);
                connection.delegate = this;
                connection.execute();
            }

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            message = getResources().getString(R.string.message_error_system);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
        }
    }

    // on change value edittext
    public void onChangeValueEditText() {
        // mobile number
        edtPaymentContractNumber.addTextChangedListener(new GenericTextWatcher(getActivity(), edtPaymentContractNumber, tvPaymentContractNumberError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_payment_enpty_number_contract)));
        edtPaymentBuyer.addTextChangedListener(new GenericTextWatcher(getActivity(), edtPaymentBuyer, tvPaymentBuyerError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_payment_empty_name)));
        edtPaymentMobileNumber.addTextChangedListener(new GenericTextWatcher(getActivity(), edtPaymentMobileNumber, tvPaymentMobileNumberError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_payment_empty_phone)));
        edtPaymentMobileNumber.addTextChangedListener(new GenericTextWatcher(getActivity(), edtPaymentMobileNumber, tvPaymentMobileNumberError, Constant.VALIDATE_PATTERN_PHONE, getResources().getString(R.string.txt_payment_wrong_format_phone)));
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getActivity());
        alert.dialogEventListener = this;
        edtPaymentContractNumber = (EditText) view.findViewById(R.id.edtPaymentContractNumber);
        edtPaymentBuyer = (EditText) view.findViewById(R.id.edtPaymentBuyer);
        edtPaymentMobileNumber = (EditText) view.findViewById(R.id.edtPaymentMobileNumber);
        tvPaymentContractNumberError = (TextView) view.findViewById(R.id.tvPaymentContractNumberError);
        tvPaymentBuyerError = (TextView) view.findViewById(R.id.tvPaymentBuyerError);
        tvPaymentMobileNumberError = (TextView) view.findViewById(R.id.tvPaymentMobileNumberError);
        tvPaymentDetailTotal = (TextView) view.findViewById(R.id.tvPaymentDetailTotal);
        btnPaymentSearch = (Button) view.findViewById(R.id.btnPaymentSearch);

        tvPaymentDetailTypeDevice = (TextView) view.findViewById(R.id.tvPaymentDetailTypeDevice);
        edtPaymentDetailFullName = (EditText) view.findViewById(R.id.edtPaymentDetailFullName);
        edtPaymentDetailTotalUnpaid = (EditText) view.findViewById(R.id.edtPaymentDetailTotalUnpaid);
        lnPaymentDetailHeader = (LinearLayout) view.findViewById(R.id.lnPaymentDetailHeader);
        exListPaymentDetailInfor = (ExpandableListView) view.findViewById(R.id.exListPaymentDetailInfor);
        rdgPaymentDetailChooseMethod = (RadioGroup) view.findViewById(R.id.rdgPaymentDetailChooseMethod);
        rdPaymentDetailNapas = (RadioButton) view.findViewById(R.id.rdPaymentDetailNapas);
        rdPaymentDetailBankBaoViet = (RadioButton) view.findViewById(R.id.rdPaymentDetailBankBaoViet);
        btnPayment = (Button) view.findViewById(R.id.btnPayment);
        rlPaymentInforDetail = (LinearLayout) view.findViewById(R.id.rlPaymentInforDetail);
        listviewPaymentDetail = (ListView) view.findViewById(R.id.listviewPaymentDetail);
        spPaymentContractNumber = (Spinner) view.findViewById(R.id.spPaymentContractNumber);
        cbPaymentTypeInput = (CheckBox) view.findViewById(R.id.cbPaymentTypeInput);

        edtPaymentBuyer.setText(StringUtil.setTextValue(fullName));
        edtPaymentMobileNumber.setText(StringUtil.setTextValue(phoneNumber));
        if (StringUtil.isNullOrEmpty(Constant.TOKEN)) {
            edtPaymentContractNumber.setVisibility(View.VISIBLE);
            spPaymentContractNumber.setVisibility(View.GONE);
            cbPaymentTypeInput.setVisibility(View.GONE);
        } else {
            if (listAccount != null) {
                edtPaymentContractNumber.setVisibility(View.GONE);
                spPaymentContractNumber.setVisibility(View.VISIBLE);
                cbPaymentTypeInput.setVisibility(View.VISIBLE);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_layout, listAccount);
                adapter.setDropDownViewResource(R.layout.spinner_layout_popup);
                spPaymentContractNumber.setAdapter(adapter);
                cbPaymentTypeInput.setOnCheckedChangeListener(this);
            } else {
                edtPaymentContractNumber.setVisibility(View.VISIBLE);
                spPaymentContractNumber.setVisibility(View.GONE);
                cbPaymentTypeInput.setVisibility(View.GONE);
            }

        }
        spPaymentContractNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                setAccountNumber(listAccount.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnPaymentSearch:
                boolean isNotEmptyNumberContract = false;
                if (cbPaymentTypeInput.isChecked() || listAccount == null || Constant.INT_0 == listAccount.size()) {
                    // Contract Number
                    isNotEmptyNumberContract = Validate.validateForm(getActivity(), edtPaymentContractNumber, tvPaymentContractNumberError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_payment_enpty_number_contract));
                }
                // Buyer
                boolean isNotEmptyFullname = Validate.validateForm(getActivity(), edtPaymentBuyer, tvPaymentBuyerError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_payment_empty_name));
                // Mobile Phone
                boolean isNotEmptyPhone = Validate.validateForm(getActivity(), edtPaymentMobileNumber, tvPaymentMobileNumberError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_payment_empty_phone));
                // validate format phone
                boolean isNotValidatePhone = Validate.validateForm(getActivity(), edtPaymentMobileNumber, tvPaymentMobileNumberError, Constant.VALIDATE_PATTERN_PHONE, getResources().getString(R.string.txt_payment_wrong_format_phone));

                if (isNotEmptyFullname && isNotEmptyPhone && isNotValidatePhone) {
                    {
                        if (cbPaymentTypeInput.isChecked() || listAccount == null || Constant.INT_0 == listAccount.size()) {
                            if (isNotEmptyNumberContract) {
                                searchInvoice();
                            } else {
                                rlPaymentInforDetail.setVisibility(View.GONE);
                            }
                        } else {
                            if (!StringUtil.isNullOrEmpty(getAccountNumber())) {
                                searchInvoice();
                            } else {
                                rlPaymentInforDetail.setVisibility(View.GONE);
                            }
                        }
                    }

                } else {
                    rlPaymentInforDetail.setVisibility(View.GONE);
                }
                break;
            case R.id.btnPayment:
                if (mListInvoiceChoose == null || Constant.INT_0 == mListInvoiceChoose.size()) {
                    message = getResources().getString(R.string.message_payment_choose_bill);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_WARNING;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                } else if (!checkListPosition(listPositionChecked)) {
                    message = getResources().getString(R.string.message_payment_warning_choose_bill);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_WARNING;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                } else {
                    if (rdPaymentDetailNapas.isChecked()) {
                        message = getResources().getString(R.string.txt_payment_confirm_payment) + Constant.SPACE + getResources().getString(R.string.txt_payment_confirm_napas);
                        typeDialog = Constant.TYPE_ALERT_DIALOG_CONFIRM_INFOR;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_PAYMENT_NAPAS, true);
                    } else if (rdPaymentDetailBankBaoViet.isChecked()) {
                        message = getResources().getString(R.string.txt_payment_confirm_payment) + Constant.SPACE + getResources().getString(R.string.txt_payment_confirm_bvbank);
                        typeDialog = Constant.TYPE_ALERT_DIALOG_CONFIRM_INFOR;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_PAYMENT_BVBANK, true);
                    }
                }
                break;
            default:
                break;
        }
    }

//    private boolean checkItemListSelected() {
//        if (listNumberSelected != null) {
//            if (Constant.INT_1 == listNumberSelected.size()) {
//                if (Constant.INT_0 == listNumberSelected.get(0)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } else if (Constant.INT_2 == listNumberSelected.size()) {
//                if (Constant.INT_0 == listNumberSelected.get(0) && Constant.INT_1 == listNumberSelected.get(1)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } else if (Constant.INT_3 <= listNumberSelected.size()) {
//                for (int i = 0; i < (listNumberSelected.size() - 1); i++) {
//                    if (listNumberSelected.get(i) != (listNumberSelected.get(i + 1) - 1)) {
//                        return false;
//                    }
//                }
//            }
//        }
//        return false;
//    }

    private void addDrawerItems(List<Invoice> listInvoice) {
        mExpandableListPaymentInforDetailAdapter = new CustomExpandableListPaymentInforDetailAdapter(getActivity(), listInvoice, tvPaymentDetailTotal);
        exListPaymentDetailInfor.setAdapter(mExpandableListPaymentInforDetailAdapter);
        //set height default of list view
        CalculateDimension.setHeightDefaultOfExpandableListView(exListPaymentDetailInfor);
        exListPaymentDetailInfor.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        exListPaymentDetailInfor.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
        exListPaymentDetailInfor.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {

                CalculateDimension.setListViewHeight(expandableListView, groupPosition);
                return false;
            }
        });

        exListPaymentDetailInfor.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }

    public void getVpcReturnUrl() {
        try {
            DataSourceConnection connection;
            Activity activity = getActivity();
            if (isAdded() && activity != null) {
                connection = new DataSourceConnection(activity, Constant.URL_GET_BACK_RETURN_URL, Constant.METHOD_POST);
                connection.delegate = this;
                connection.execute();
            }

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            message = getResources().getString(R.string.message_error_system);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
        }
    }

    public boolean checkListPosition(SortedSet<Integer> list) {
        boolean flag = false;
        if (list == null) {
            return false;
        } else {
            if (Constant.INT_0 == list.size()) {
                return false;
            } else if (Constant.INT_1 == list.size()) {
                if (Constant.INT_0 == list.first()) {
                    return true;
                } else {
                    return false;
                }
            } else if (Constant.INT_1 < list.size()) {
                int i = 0;
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if ((Integer) it.next() != i) {
                        return false;
                    } else {
                        flag = true;
                    }
                    i++;
                }
            }
        }
        return flag;
    }

    @Override
    public void processFinish(String result, String urlApi) {
        try {
            if (!StringUtil.isNullOrEmpty(result)) {
                if (Constant.ERROR_SERVER.equals(result)) {
                    message = getResources().getString(R.string.message_error_server);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                } else if (Constant.ERROR_NETWORK.equals(result)) {
                    rlPaymentInforDetail.setVisibility(View.GONE);
                    message = getResources().getString(R.string.message_error_network);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                } else {
                    JSONObject jsonObject = new JSONObject(result);
                    int responseStatus = jsonObject.getInt(Keys.KEY_RESPONSE_STATUS);
                    String responseMeessage = jsonObject.getString(Keys.KEY_RESPONSE_MESSAGE);
                    if (urlApi.equals(Constant.URL_SEARCH_INVOICE)) {
                        if (Constant.RESPONSE_CODE_200 == responseStatus) {
                            JSONObject objResult = jsonObject.getJSONObject(Keys.KEY_OBJECT);
                            Gson gsonDTO = new GsonBuilder().create();
                            InvoiceDTO invoiceDTO = gsonDTO.fromJson(objResult.toString(), InvoiceDTO.class);

                            edtPaymentDetailFullName.setText(StringUtil.setTextValue(invoiceDTO.getPolicyHolder()));
                            edtPaymentDetailTotalUnpaid.setText(StringUtil.convertToCurrency(invoiceDTO.getInvoiceAmountSum()));
                            List<Invoice> myModelList = invoiceDTO.getInvoiceList();

                            if (myModelList == null) {
                                rlPaymentInforDetail.setVisibility(View.GONE);
                                message = getResources().getString(R.string.message_payment_no_data);
                                typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                            } else if (Constant.INT_0 == myModelList.size()) {
                                rlPaymentInforDetail.setVisibility(View.GONE);
                                message = getResources().getString(R.string.message_payment_no_data_in_30days);
                                typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                            } else {
                                rlPaymentInforDetail.setVisibility(View.VISIBLE);
                                if (Constant.TYPE_DEVICE_TABLET.equals(tvPaymentDetailTypeDevice.getText())) {
                                    mDetailInforPaymentAdapter = new CustomDetailInforPaymentAdapter(getActivity(), myModelList, tvPaymentDetailTotal);
                                    listviewPaymentDetail.setAdapter(mDetailInforPaymentAdapter);
                                    CalculateDimension.setListViewHeightBasedOnChildren(listviewPaymentDetail);
                                } else if (Constant.TYPE_DEVICE_PHONE.equals(tvPaymentDetailTypeDevice.getText())) {
                                    addDrawerItems(myModelList);
                                }
                            }
                        } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                            rlPaymentInforDetail.setVisibility(View.GONE);
                            message = responseMeessage;
                            typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                        } else {
                            rlPaymentInforDetail.setVisibility(View.GONE);
                            if (StringUtil.isNullOrEmpty(responseMeessage)) {
                                message = getResources().getString(R.string.message_payment_no_data);
                            } else {
                                message = responseMeessage;
                            }
                            typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                        }
                    } else if (urlApi.equals(Constant.URL_PAYMENT_NAPAS)) {
                        if (Constant.RESPONSE_CODE_200 == responseStatus) {
                            linkNapas = jsonObject.getString(Keys.KEY_OBJECT);
                            if (StringUtil.isNullOrEmpty(linkNapas)) {
                                message = getResources().getString(R.string.message_error_system);
                                typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                            } else {
                                getVpcReturnUrl();
                            }

                        } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                            message = responseMeessage;
                            typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                        } else {
                            message = getResources().getString(R.string.message_payment_unsucess);
                            typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                        }
                    } else if (urlApi.equals(Constant.URL_PAYMENT_BAOVIET_BANK)) {
                        if (Constant.RESPONSE_CODE_200 == responseStatus) {
                            String link = jsonObject.getString(Keys.KEY_OBJECT);
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                            startActivity(browserIntent);
                        } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                            message = responseMeessage;
                            typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                        } else {
                            message = getResources().getString(R.string.message_payment_unsucess);
                            typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                        }
                    } else if (urlApi.equals(Constant.URL_GET_BACK_RETURN_URL)) {
                        if (Constant.RESPONSE_CODE_200 == responseStatus) {
                            JSONArray arrResult = jsonObject.getJSONArray(Keys.KEY_OBJECT);
                            Gson gson = new GsonBuilder().create();
                            Type listType = new TypeToken<List<KeyValueDTO>>() {
                            }.getType();
                            List<KeyValueDTO> myModelList = gson.fromJson(arrResult.toString(), listType);
                            if (myModelList != null && Constant.INT_0 < myModelList.size()) {
                                boolean flag = false;
                                for (int i = 0; i < myModelList.size(); i++) {
                                    if (myModelList.get(i).getValue().equals(Constant.VPC_RETURN_URL)) {
                                        flag = true;
                                        urlReturn = myModelList.get(i).getLabel();
                                        if (StringUtil.isNullOrEmpty(urlReturn)) {
                                            message = getResources().getString(R.string.message_error_system);
                                            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                                        } else {
                                            Bundle bundleNapas = new Bundle();
                                            bundleNapas.putString(Keys.KEY_WEB_LINK_NAPAS, linkNapas);
                                            bundleNapas.putString(Keys.KEY_WEB_BACK_RETURN_URL, urlReturn);
                                            if (!StringUtil.isNullOrEmpty(Constant.TOKEN)) {
                                                bundleNapas.putString(Keys.KEY_PHONE_NUMBER, this.phoneNumber);
                                                bundleNapas.putString(Keys.KEY_FULLNAME, this.fullName);
                                                bundleNapas.putStringArrayList(Keys.KEY_LIST_ACCOUNT_NUMBER, (ArrayList<String>) listAccount);
                                            }
                                            PaymentNapasFragment fr = new PaymentNapasFragment();
                                            fr.setArguments(bundleNapas);
                                            FragmentManager fm = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                            fragmentTransaction.replace(R.id.frameContent, fr);
                                            fragmentTransaction.addToBackStack(Constant.TRANSFER_TO_PAYMENT_NAPAS);
                                            fragmentTransaction.commit();
                                        }
                                        break;
                                    }
                                }
                                if (!flag) {
                                    message = responseMeessage;
                                    typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                                }
                            }
                        } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                            message = responseMeessage;
                            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                        } else {
                            message = getResources().getString(R.string.message_error_system);
                            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                        }
                    }
                }
            } else {
                message = getResources().getString(R.string.message_error_system);
                typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            message = getResources().getString(R.string.message_error_system);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
        }
    }

    @Override
    public void processFinishStream(ResponseStream inputStream) {

    }

    @Override
    public void OnClickedButtonDialog(boolean isClickOk, String actionDialog, String typeDialog) {
        if (isClickOk) {
            switch (actionDialog) {
                case Constant.ACTION_DIALOG_PAYMENT_NAPAS:
                    paymentBillNapas();
                    break;
                case Constant.ACTION_DIALOG_PAYMENT_BVBANK:
                    paymentBillBaoVietBank();
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (cbPaymentTypeInput.isChecked()) {
            spPaymentContractNumber.setVisibility(View.GONE);
            edtPaymentContractNumber.setVisibility(View.VISIBLE);
        } else {
            spPaymentContractNumber.setVisibility(View.VISIBLE);
            edtPaymentContractNumber.setVisibility(View.GONE);
            tvPaymentContractNumberError.setVisibility(View.GONE);
        }
    }
}