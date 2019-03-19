package app.com.baoviet.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.adapter.CustomExpandableListBeneficiaryiAdapter;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.entity.BeneficiaryDTO;
import app.com.baoviet.entity.CustomerInfoDTO;
import app.com.baoviet.entity.LifeInsuredDTO;
import app.com.baoviet.entity.PolicyOwnerDTO;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.CalculateDimension;
import app.com.baoviet.utility.StringUtil;

public class TabContractCustomerInforFragment extends Fragment implements AsyncResponse, DialogEventListener, View.OnTouchListener {

    private EditText edtContractTabCustomerName;
    private EditText edtContractTabCustomerRepresentative;
    private EditText edtContractTabCustomerMail;
    private EditText edtContractTabCustomerPhone;
    private EditText edtContractTabCustomerPhoneCompany;
    private EditText edtContractTabCustomerTypeId;
    private EditText edtContractTabCustomerNumber;
    private EditText edtContractTabCustomerDateId;
    private EditText edtContractTabCustomerAddressId;
    private EditText edtContractTabCustomerTaxCode;
    private EditText edtContractTabCustomerAddress;
    private EditText edtContractTabCustomerAssignName;
    private EditText edtContractTabCustomerAssignBirthday;
    private EditText edtContractTabCustomerAssignGender;
    private EditText edtContractTabCustomerAssignMail;
    private EditText edtContractTabCustomerAssignPhone;
    private EditText edtContractTabCustomerAssignPhoneCompany;
    private EditText edtContractTabCustomerAssignHomePhone;
    private EditText edtContractTabCustomerAssignTypeId;
    private EditText edtContractTabCustomerAssignNumber;
    private EditText edtContractTabCustomerAssignDateId;
    private EditText edtContractTabCustomerAssignAddressId;
    private EditText edtContractTabCustomerAssignTaxCode;
    private EditText edtContractTabCustomerAssignAddress;
    private EditText edtContractTabCustomerBirthday;
    private EditText edtContractTabCustomerHomePhone;
    private EditText edtContractTabCustomerGender;

    private TextView tvContractTabCustomerBuyer;
    private TextView tvContractTabCustomerAssign;
    private TextView tvContractTabCustomerBeneficiary;
    private TextView tvContractTabCustomerAddress;
    private TextView tvContractTabCustomerAssignAddress;
    private TextView tvContractTabTitleCustomerBuyer;

    private LinearLayout lnContractTabCustomerAssign;
    private LinearLayout lnContractTabCustomerBeneficiary;
    private LinearLayout lnContractTabCustomerBuyer;
    private LinearLayout lnContractTabCustomerRepresentative;
    private LinearLayout lnContractTabCustomerBirthday;
    private LinearLayout lnContractTabCustomerGender;
    private LinearLayout lnContractTabCustomerHomePhone;
    private LinearLayout lnContractTabCustomerAssignBirthday;
    private LinearLayout lnContractTabCustomerAssignGender;
    private LinearLayout lnContractTabCustomerAssignHomePhone;
    private LinearLayout lnContractTabCustomerTitleAssign;

    private ExpandableListView exListBeneficiary;
    private CustomExpandableListBeneficiaryiAdapter mExpandableListBeneficiaryAdapter;
    private String typeAccount;

    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;


    private String accountNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_contract_customer_infor, container, false);
        initInterface(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            accountNumber = bundle.getString(Keys.KEY_ACCOUNT_NUMBER);
            typeAccount = bundle.getString(Keys.KEY_TYPE_ACCOUNT);
        }
        getData();
        return view;
    }

    public void getData() {
        try {
            if (accountNumber != null) {
                Activity activity = getActivity();
                if (isAdded() && activity != null) {
                    DataSourceConnection connection;
                    connection = new DataSourceConnection(activity, Constant.URL_GET_CUSTOMER_INFOR, accountNumber, Constant.METHOD_POST);
                    connection.delegate = this;
                    connection.execute();
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

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getContext());
        alert.dialogEventListener = this;
        edtContractTabCustomerName = (EditText) view.findViewById(R.id.edtContractTabCustomerName);
        edtContractTabCustomerRepresentative = (EditText) view.findViewById(R.id.edtContractTabCustomerRepresentative);
        edtContractTabCustomerMail = (EditText) view.findViewById(R.id.edtContractTabCustomerMail);
        edtContractTabCustomerPhone = (EditText) view.findViewById(R.id.edtContractTabCustomerPhone);
        edtContractTabCustomerPhoneCompany = (EditText) view.findViewById(R.id.edtContractTabCustomerPhoneCompany);
        edtContractTabCustomerTypeId = (EditText) view.findViewById(R.id.edtContractTabCustomerTypeId);
        edtContractTabCustomerNumber = (EditText) view.findViewById(R.id.edtContractTabCustomerNumber);
        edtContractTabCustomerDateId = (EditText) view.findViewById(R.id.edtContractTabCustomerDateId);
        edtContractTabCustomerAddressId = (EditText) view.findViewById(R.id.edtContractTabCustomerAddressId);
        edtContractTabCustomerTaxCode = (EditText) view.findViewById(R.id.edtContractTabCustomerTaxCode);
        edtContractTabCustomerAddress = (EditText) view.findViewById(R.id.edtContractTabCustomerAddress);
        edtContractTabCustomerAssignName = (EditText) view.findViewById(R.id.edtContractTabCustomerAssignName);
        edtContractTabCustomerAssignBirthday = (EditText) view.findViewById(R.id.edtContractTabCustomerAssignBirthday);
        edtContractTabCustomerAssignGender = (EditText) view.findViewById(R.id.edtContractTabCustomerAssignGender);
        edtContractTabCustomerAssignMail = (EditText) view.findViewById(R.id.edtContractTabCustomerAssignMail);
        edtContractTabCustomerAssignPhone = (EditText) view.findViewById(R.id.edtContractTabCustomerAssignPhone);
        edtContractTabCustomerAssignPhoneCompany = (EditText) view.findViewById(R.id.edtContractTabCustomerAssignPhoneCompany);
        edtContractTabCustomerAssignHomePhone = (EditText) view.findViewById(R.id.edtContractTabCustomerAssignHomePhone);
        edtContractTabCustomerAssignTypeId = (EditText) view.findViewById(R.id.edtContractTabCustomerAssignTypeId);
        edtContractTabCustomerAssignNumber = (EditText) view.findViewById(R.id.edtContractTabCustomerAssignNumber);
        edtContractTabCustomerAssignDateId = (EditText) view.findViewById(R.id.edtContractTabCustomerAssignDateId);
        edtContractTabCustomerAssignAddressId = (EditText) view.findViewById(R.id.edtContractTabCustomerAssignAddressId);
        edtContractTabCustomerAssignTaxCode = (EditText) view.findViewById(R.id.edtContractTabCustomerAssignTaxCode);
        edtContractTabCustomerAssignAddress = (EditText) view.findViewById(R.id.edtContractTabCustomerAssignAddress);
        lnContractTabCustomerBuyer = (LinearLayout) view.findViewById(R.id.lnContractTabCustomerBuyer);
        tvContractTabCustomerBuyer = (TextView) view.findViewById(R.id.tvContractTabCustomerBuyer);
        lnContractTabCustomerAssign = (LinearLayout) view.findViewById(R.id.lnContractTabCustomerAssign);
        tvContractTabCustomerAssign = (TextView) view.findViewById(R.id.tvContractTabCustomerAssign);
        lnContractTabCustomerBeneficiary = (LinearLayout) view.findViewById(R.id.lnContractTabCustomerBeneficiary);
        tvContractTabCustomerBeneficiary = (TextView) view.findViewById(R.id.tvContractTabCustomerBeneficiary);
        edtContractTabCustomerBirthday = (EditText) view.findViewById(R.id.edtContractTabCustomerBirthday);
        edtContractTabCustomerHomePhone = (EditText) view.findViewById(R.id.edtContractTabCustomerHomePhone);
        edtContractTabCustomerGender = (EditText) view.findViewById(R.id.edtContractTabCustomerGender);
        tvContractTabCustomerAddress = (TextView) view.findViewById(R.id.tvContractTabCustomerAddress);
        tvContractTabCustomerAssignAddress = (TextView) view.findViewById(R.id.tvContractTabCustomerAssignAddress);
        tvContractTabTitleCustomerBuyer = (TextView) view.findViewById(R.id.tvContractTabTitleCustomerBuyer);
        lnContractTabCustomerRepresentative = (LinearLayout) view.findViewById(R.id.lnContractTabCustomerRepresentative);
        lnContractTabCustomerBirthday = (LinearLayout) view.findViewById(R.id.lnContractTabCustomerBirthday);
        lnContractTabCustomerGender = (LinearLayout) view.findViewById(R.id.lnContractTabCustomerGender);
        lnContractTabCustomerHomePhone = (LinearLayout) view.findViewById(R.id.lnContractTabCustomerHomePhone);
        lnContractTabCustomerAssignBirthday = (LinearLayout) view.findViewById(R.id.lnContractTabCustomerAssignBirthday);
        lnContractTabCustomerAssignGender = (LinearLayout) view.findViewById(R.id.lnContractTabCustomerAssignGender);
        lnContractTabCustomerAssignHomePhone = (LinearLayout) view.findViewById(R.id.lnContractTabCustomerAssignHomePhone);
        lnContractTabCustomerTitleAssign = (LinearLayout) view.findViewById(R.id.lnContractTabCustomerTitleAssign);
        exListBeneficiary = (ExpandableListView) view.findViewById(R.id.exListBeneficiary);
        edtContractTabCustomerAddress.setOnTouchListener(this);
        edtContractTabCustomerAssignAddress.setOnTouchListener(this);
    }

    public void showData(List<CustomerInfoDTO> listCustomerInfor) {
        if (listCustomerInfor == null) {
            //nodata
            tvContractTabCustomerBuyer.setVisibility(View.VISIBLE);
            lnContractTabCustomerBuyer.setVisibility(View.GONE);
            tvContractTabCustomerAssign.setVisibility(View.VISIBLE);
            lnContractTabCustomerAssign.setVisibility(View.GONE);
            tvContractTabCustomerBeneficiary.setVisibility(View.VISIBLE);
            tvContractTabCustomerBeneficiary.setText(getResources().getString(R.string.txt_contract_tab_account_value_error));
            lnContractTabCustomerBeneficiary.setVisibility(View.GONE);
        } else {
            if (Constant.INT_0 == listCustomerInfor.size()) {
                //nodata
                tvContractTabCustomerBuyer.setVisibility(View.VISIBLE);
                lnContractTabCustomerBuyer.setVisibility(View.GONE);
                tvContractTabCustomerAssign.setVisibility(View.VISIBLE);
                lnContractTabCustomerAssign.setVisibility(View.GONE);
                tvContractTabCustomerBeneficiary.setVisibility(View.VISIBLE);
                tvContractTabCustomerBeneficiary.setText(getResources().getString(R.string.txt_contract_tab_account_value_error));
                lnContractTabCustomerBeneficiary.setVisibility(View.GONE);
            } else {
                PolicyOwnerDTO ownerObj = listCustomerInfor.get(Constant.INT_0).getPolicyOwnerList();
                LifeInsuredDTO insureObj = listCustomerInfor.get(Constant.INT_0).getLifeInsuredList();
                List<BeneficiaryDTO> listBeneficiary = listCustomerInfor.get(Constant.INT_0).getBeneficiaryInfoList();
                boolean isOwnerLife = listCustomerInfor.get(Constant.INT_0).isOwnerLife();

                if (ownerObj == null) {
                    tvContractTabCustomerBuyer.setVisibility(View.VISIBLE);
                    lnContractTabCustomerBuyer.setVisibility(View.GONE);
                } else {
                    tvContractTabCustomerBuyer.setVisibility(View.GONE);
                    lnContractTabCustomerBuyer.setVisibility(View.VISIBLE);

                    //check client type code equal 'COMP' or not
                    if (ownerObj.getClientTypeCode().equals(Constant.CLIENT_TYPE_CODE_COMP)) {
                        lnContractTabCustomerRepresentative.setVisibility(View.VISIBLE);
                        lnContractTabCustomerBirthday.setVisibility(View.GONE);
                        lnContractTabCustomerGender.setVisibility(View.GONE);
                        lnContractTabCustomerHomePhone.setVisibility(View.GONE);
                        edtContractTabCustomerRepresentative.setText(ownerObj.getClientRepresentativeName());

                    } else {
                        lnContractTabCustomerRepresentative.setVisibility(View.GONE);
                        lnContractTabCustomerBirthday.setVisibility(View.VISIBLE);
                        lnContractTabCustomerGender.setVisibility(View.VISIBLE);
                        lnContractTabCustomerHomePhone.setVisibility(View.VISIBLE);
                        edtContractTabCustomerBirthday.setText(StringUtil.formatDateDDMMYYFromDate(ownerObj.getClientDateOfBirth(), Constant.DDMMYY));
                        edtContractTabCustomerGender.setText(ownerObj.getClientSexDescription());
                        edtContractTabCustomerHomePhone.setText(ownerObj.getClientHomePhone());
                    }
                    // check null of value addrAddressTypeDesc
                    if (!StringUtil.isNullOrEmpty(ownerObj.getClntAddrs().getAddrAddressTypeDesc())) {
                        tvContractTabCustomerAddress.setText(ownerObj.getClntAddrs().getAddrAddressTypeDesc());
                    } else {
                        tvContractTabCustomerAddress.setText(getActivity().getString(R.string.txt_contract_tab_customer_infor_buyer_label_address));
                    }
                    edtContractTabCustomerName.setText(ownerObj.getClientName());
                    edtContractTabCustomerMail.setText(ownerObj.getClientEmail());
                    edtContractTabCustomerPhone.setText(ownerObj.getClientMobilePhone());
                    edtContractTabCustomerPhoneCompany.setText(ownerObj.getClientBusinessPhone());
                    edtContractTabCustomerTypeId.setText(StringUtil.getClientIdentityDoctypeFromCode(ownerObj.getClientIdentityDoctype()));
                    edtContractTabCustomerNumber.setText(ownerObj.getClientIdnumber());
                    edtContractTabCustomerDateId.setText(StringUtil.formatDateDDMMYYFromDate(ownerObj.getClientDateOfIssue(), Constant.DDMMYY));
                    edtContractTabCustomerAddressId.setText(ownerObj.getClientIssuingAuthority());
                    edtContractTabCustomerTaxCode.setText(ownerObj.getClientTaxCode());
                    edtContractTabCustomerAddress.setText(ownerObj.getClntAddrs().getAddrFullAddress());
                }

                if (isOwnerLife) {
                    lnContractTabCustomerTitleAssign.setVisibility(View.GONE);
                    lnContractTabCustomerAssign.setVisibility(View.GONE);
                    tvContractTabTitleCustomerBuyer.setText(R.string.txt_contract_tab_customer_infor_title_buyer_assign);
                } else {
                    lnContractTabCustomerTitleAssign.setVisibility(View.VISIBLE);
                    lnContractTabCustomerAssign.setVisibility(View.VISIBLE);
                    tvContractTabTitleCustomerBuyer.setText(R.string.txt_contract_tab_customer_infor_title_buyer);
                    //check infor insure
                    if (insureObj == null) {
                        tvContractTabCustomerAssign.setVisibility(View.VISIBLE);
                        lnContractTabCustomerAssign.setVisibility(View.GONE);
                    } else {
                        tvContractTabCustomerAssign.setVisibility(View.GONE);
                        lnContractTabCustomerAssign.setVisibility(View.VISIBLE);

                        if (!insureObj.getClientTypeCode().equals(Constant.CLIENT_TYPE_CODE_COMP)) {
                            lnContractTabCustomerAssignBirthday.setVisibility(View.VISIBLE);
                            lnContractTabCustomerAssignGender.setVisibility(View.VISIBLE);
                            lnContractTabCustomerAssignHomePhone.setVisibility(View.VISIBLE);
                            edtContractTabCustomerAssignBirthday.setText(StringUtil.formatDateDDMMYYFromDate(insureObj.getClientDateOfBirth(), Constant.DDMMYY));
                            edtContractTabCustomerAssignGender.setText(insureObj.getClientSexDescription());
                            edtContractTabCustomerAssignHomePhone.setText(insureObj.getClientHomePhone());
                        }
                        edtContractTabCustomerAssignName.setText(insureObj.getClientName());
                        edtContractTabCustomerAssignMail.setText(insureObj.getClientEmail());
                        edtContractTabCustomerAssignPhone.setText(insureObj.getClientMobilePhone());
                        edtContractTabCustomerAssignPhoneCompany.setText(insureObj.getClientBusinessPhone());
                        edtContractTabCustomerAssignTypeId.setText(StringUtil.getClientIdentityDoctypeFromCode(insureObj.getClientIdentityDoctype()));
                        edtContractTabCustomerAssignNumber.setText(insureObj.getClientIdnumber());
                        edtContractTabCustomerAssignDateId.setText(StringUtil.formatDateDDMMYYFromDate(insureObj.getClientDateOfIssue(), Constant.DDMMYY));
                        edtContractTabCustomerAssignAddressId.setText(insureObj.getClientIssuingAuthority());
                        edtContractTabCustomerAssignTaxCode.setText(insureObj.getClientTaxCode());
                        // check null of value addrAddressTypeDesc
                        if (!StringUtil.isNullOrEmpty(insureObj.getClntAddrs().getAddrAddressTypeDesc())) {
                            tvContractTabCustomerAssignAddress.setText(insureObj.getClntAddrs().getAddrAddressTypeDesc());
                        } else {
                            tvContractTabCustomerAssignAddress.setText(getActivity().getString(R.string.txt_contract_tab_customer_infor_buyer_label_address));
                        }
                        edtContractTabCustomerAssignAddress.setText(insureObj.getClntAddrs().getAddrFullAddress());
                    }
                }


                //check infor beneficiary
                if (listBeneficiary == null) {
                    tvContractTabCustomerBeneficiary.setVisibility(View.VISIBLE);
                    tvContractTabCustomerBeneficiary.setText(getResources().getString(R.string.txt_contract_tab_account_value_error));
                    lnContractTabCustomerBeneficiary.setVisibility(View.GONE);
                } else {
                    if (Constant.INT_0 == listBeneficiary.size()) {
                        tvContractTabCustomerBeneficiary.setVisibility(View.VISIBLE);
                        tvContractTabCustomerBeneficiary.setText(getResources().getString(R.string.txt_contract_tab_account_value_empty));
                        lnContractTabCustomerBeneficiary.setVisibility(View.GONE);
                    } else {
                        tvContractTabCustomerBeneficiary.setVisibility(View.GONE);
                        lnContractTabCustomerBeneficiary.setVisibility(View.VISIBLE);
                        addDrawerItems(listBeneficiary);
                    }
                }
            }
        }

    }


    private void addDrawerItems(List<BeneficiaryDTO> listBeneficiary) {
        Context context = getContext();
        mExpandableListBeneficiaryAdapter = new CustomExpandableListBeneficiaryiAdapter(context, listBeneficiary);
        exListBeneficiary.setAdapter(mExpandableListBeneficiaryAdapter);
        //set height default of list view
        CalculateDimension.setHeightDefaultOfExpandableListView(exListBeneficiary);
        exListBeneficiary.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        exListBeneficiary.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
        exListBeneficiary.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {

                CalculateDimension.setListViewHeight(expandableListView, groupPosition);
                return false;
            }
        });

        exListBeneficiary.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }

    @Override
    public void processFinish(String result, String urlApi) {
        try {
            if (!StringUtil.isNullOrEmpty(result)) {
                if (Constant.ERROR_SERVER.equals(result)) {
                    message = getResources().getString(R.string.message_error_server);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                }
                JSONObject jsonObject = new JSONObject(result);
                int responseStatus = jsonObject.getInt(Keys.KEY_RESPONSE_STATUS);
                String responseMessage = jsonObject.getString(Keys.KEY_RESPONSE_MESSAGE);
                if (Constant.RESPONSE_CODE_200 == responseStatus) {
                    JSONArray arrResult = jsonObject.getJSONArray(Keys.KEY_OBJECT);
                    Gson gson = new GsonBuilder().create();
                    Type listType = new TypeToken<List<CustomerInfoDTO>>() {
                    }.getType();
                    List<CustomerInfoDTO> myModelList = gson.fromJson(arrResult.toString(), listType);
                    //show data
                    showData(myModelList);
                } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                    message = responseMessage;
                    typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                } else {
                    message = getResources().getString(R.string.message_infor_customer_no_data);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
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
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.edtContractTabCustomerAddress:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.popup_text, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        TextView tvContentPopup = (TextView) popupView.findViewById(R.id.tvContentPopup);

                        tvContentPopup.setText(edtContractTabCustomerAddress.getText().toString());
                        popupWindow.showAsDropDown(view, 0, 0, Gravity.BOTTOM);
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.setFocusable(true);
                        popupWindow.update();
                    }
                }
                break;
            case R.id.edtContractTabCustomerAssignAddress:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.popup_text, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        TextView tvContentPopup = (TextView) popupView.findViewById(R.id.tvContentPopup);

                        tvContentPopup.setText(edtContractTabCustomerAssignAddress.getText().toString());
                        popupWindow.showAsDropDown(view, 0, 0, Gravity.BOTTOM);
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.setFocusable(true);
                        popupWindow.update();
                    }
                }
                break;
        }
        return false;
    }
}