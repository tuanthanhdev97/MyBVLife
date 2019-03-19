package app.com.baoviet.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.adapter.CustomExpandableListMeritValueAdapter;
import app.com.baoviet.adapter.CustomExpandableListValueAccountiAdapter;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.entity.LoanDTO;
import app.com.baoviet.entity.PavDTO;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.CalculateDimension;
import app.com.baoviet.utility.StringUtil;

public class TabContractValueAccountFragment extends Fragment implements AsyncResponse, DialogEventListener, NavigationView.OnNavigationItemSelectedListener {


    private TextView tvContractTabAccountValueNoData;
    private TextView tvContractTabAccountValueMsgResponse;
    private TextView tvContractTabAccountValueTitle;
    private ExpandableListView exListAccountValue;

    private TextView tvContractTabMeritValueNoData;
    private TextView tvContractTabMeritValueMsgResponse;
    private LinearLayout lnContractTabAccountValueConcession;
    private LinearLayout lnContractTabAccountValueNote;
    private ExpandableListView exListMeritValue;

    private String accountNumber;
    private String typeAccount;
    private String productTypeCode;
    private List<PavDTO> listPav = new ArrayList<PavDTO>();
    private List<LoanDTO> listLoan = new ArrayList<LoanDTO>();
    private CustomExpandableListValueAccountiAdapter mExpandableListValueAccountAdapter;
    private CustomExpandableListMeritValueAdapter mExpandableListMeritValueAdapterAdapter;

    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_contract_value_account, container, false);
        initInterface(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            accountNumber = bundle.getString(Keys.KEY_ACCOUNT_NUMBER);
            typeAccount = bundle.getString(Keys.KEY_TYPE_ACCOUNT);
            productTypeCode = bundle.getString(Keys.KEY_PRODUCT_TYPE_CODE);
        }
        showData();
        return view;
    }

    public void showData() {
        if (Constant.TYPE_ACCOUNT_TALISMAN.equals(typeAccount)) {
            tvContractTabAccountValueTitle.setText(R.string.txt_contract_tab_account_value_title_value_account);
//            lnContractTabAccountValueNote.setVisibility(View.GONE);
            getInitData(Constant.URL_GET_PAV);
            getInitData(Constant.URL_GET_LOAN);

        } else if (Constant.TYPE_ACCOUNT_BVLIFE.equals(typeAccount)) {
            tvContractTabAccountValueTitle.setText(R.string.txt_contract_tab_account_value_title_confession);
//            lnContractTabAccountValueNote.setVisibility(View.VISIBLE);
            lnContractTabAccountValueConcession.setVisibility(View.GONE);
            exListMeritValue.setVisibility(View.GONE);
            getInitData(Constant.URL_GET_LOAN);
        }
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getContext());
        alert.dialogEventListener = this;
        tvContractTabAccountValueNoData = (TextView) view.findViewById(R.id.tvContractTabAccountValueNoData);
        tvContractTabAccountValueMsgResponse = (TextView) view.findViewById(R.id.tvContractTabAccountValueMsgResponse);
        tvContractTabAccountValueTitle = (TextView) view.findViewById(R.id.tvContractTabAccountValueTitle);
        exListAccountValue = (ExpandableListView) view.findViewById(R.id.exListAccountValue);
        tvContractTabMeritValueNoData = (TextView) view.findViewById(R.id.tvContractTabMeritValueNoData);
        tvContractTabMeritValueMsgResponse = (TextView) view.findViewById(R.id.tvContractTabMeritValueMsgResponse);
        lnContractTabAccountValueConcession = (LinearLayout) view.findViewById(R.id.lnContractTabAccountValueConcession);
        lnContractTabAccountValueNote = (LinearLayout) view.findViewById(R.id.lnContractTabAccountValueNote);
        exListMeritValue = (ExpandableListView) view.findViewById(R.id.exListMeritValue);
    }

    private void addDrawerItems(String urlApi) {
        if (Constant.URL_GET_PAV.equals(urlApi)) {
            mExpandableListValueAccountAdapter = new CustomExpandableListValueAccountiAdapter(getContext(), listPav, productTypeCode);
            exListAccountValue.setAdapter(mExpandableListValueAccountAdapter);
            //set height default of list view
            CalculateDimension.setHeightDefaultOfExpandableListView(exListAccountValue);
            exListAccountValue.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {

                }
            });

            exListAccountValue.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                @Override
                public void onGroupCollapse(int groupPosition) {
                }
            });
            exListAccountValue.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {

                    CalculateDimension.setListViewHeight(expandableListView, groupPosition);
                    return false;
                }
            });

            exListAccountValue.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    return false;
                }
            });
        } else if (Constant.URL_GET_LOAN.equals(urlApi)) {
            mExpandableListMeritValueAdapterAdapter = new CustomExpandableListMeritValueAdapter(getContext(), listLoan);
            exListMeritValue.setAdapter(mExpandableListMeritValueAdapterAdapter);
            //set height default of list view
            CalculateDimension.setHeightDefaultOfExpandableListView(exListMeritValue);
            exListMeritValue.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {

                }
            });

            exListMeritValue.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                @Override
                public void onGroupCollapse(int groupPosition) {
                }
            });
            exListMeritValue.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {

                    return false;
                }
            });

            exListMeritValue.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    return false;
                }
            });
        }

    }

    public void getInitData(String urlApi) {
        try {
            if (accountNumber != null) {
                Activity activity = getActivity();
                if (isAdded() && activity != null) {
                    DataSourceConnection connection;
                    connection = new DataSourceConnection(activity, urlApi, accountNumber, Constant.METHOD_POST);
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

    @Override
    public void processFinish(String result, String urlApi) {

        if (urlApi.equals(Constant.URL_GET_PAV)) {
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
                    if (Constant.INT_200 == responseStatus) {
                        exListAccountValue.setVisibility(View.VISIBLE);
                        tvContractTabAccountValueNoData.setVisibility(View.GONE);
                        tvContractTabAccountValueMsgResponse.setVisibility(View.GONE);
                        JSONArray arrResult = jsonObject.getJSONArray(Keys.KEY_OBJECT);
                        Gson gson = new GsonBuilder().create();
                        Type listType = new TypeToken<List<PavDTO>>() {
                        }.getType();
                        List<PavDTO> myModelList = gson.fromJson(arrResult.toString(), listType);
                        if (Constant.INT_0 == myModelList.size()) {
                            tvContractTabAccountValueMsgResponse.setVisibility(View.GONE);
                            tvContractTabAccountValueNoData.setVisibility(View.VISIBLE);
                            exListAccountValue.setVisibility(View.GONE);
                        } else {
                            tvContractTabAccountValueMsgResponse.setVisibility(View.GONE);
                            tvContractTabAccountValueNoData.setVisibility(View.GONE);
                            exListAccountValue.setVisibility(View.VISIBLE);
                            listPav = myModelList;
                            //show data
                            addDrawerItems(urlApi);
                        }
                    } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                        tvContractTabAccountValueMsgResponse.setVisibility(View.VISIBLE);
                        tvContractTabAccountValueNoData.setVisibility(View.GONE);
                        exListAccountValue.setVisibility(View.GONE);
                        tvContractTabAccountValueMsgResponse.setText(responseMessage);
                    } else {
                        tvContractTabAccountValueMsgResponse.setVisibility(View.GONE);
                        tvContractTabAccountValueNoData.setVisibility(View.VISIBLE);
                        exListAccountValue.setVisibility(View.GONE);
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

        } else if (urlApi.equals(Constant.URL_GET_LOAN)) {
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
                    if (Constant.INT_200 == responseStatus) {
                        exListMeritValue.setVisibility(View.VISIBLE);
                        tvContractTabMeritValueNoData.setVisibility(View.GONE);
                        tvContractTabMeritValueMsgResponse.setVisibility(View.GONE);
                        JSONArray arrResult = jsonObject.getJSONArray(Keys.KEY_OBJECT);
                        Gson gson = new GsonBuilder().create();
                        Type listType = new TypeToken<List<LoanDTO>>() {
                        }.getType();
                        List<LoanDTO> myModelList = gson.fromJson(arrResult.toString(), listType);
                        if (Constant.INT_0 == myModelList.size()) {
                            tvContractTabMeritValueMsgResponse.setVisibility(View.GONE);
                            tvContractTabMeritValueNoData.setVisibility(View.VISIBLE);
                            exListMeritValue.setVisibility(View.GONE);
                        } else {
                            tvContractTabMeritValueMsgResponse.setVisibility(View.GONE);
                            tvContractTabMeritValueNoData.setVisibility(View.GONE);
                            exListMeritValue.setVisibility(View.VISIBLE);
                            listLoan = myModelList;
                            //show data
                            addDrawerItems(urlApi);
                        }
                    } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                        tvContractTabMeritValueMsgResponse.setVisibility(View.VISIBLE);
                        tvContractTabMeritValueNoData.setVisibility(View.GONE);
                        exListMeritValue.setVisibility(View.GONE);
                        tvContractTabMeritValueMsgResponse.setText(responseMessage);
                    } else {
                        tvContractTabMeritValueMsgResponse.setVisibility(View.GONE);
                        tvContractTabMeritValueNoData.setVisibility(View.VISIBLE);
                        exListMeritValue.setVisibility(View.GONE);
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
    }

    @Override
    public void processFinishStream(ResponseStream inputStream) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void OnClickedButtonDialog(boolean isClickOk, String actionDialog, String typeDialog) {
    }
}