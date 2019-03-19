package app.com.baoviet.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
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
import app.com.baoviet.adapter.CustomExpandableListBenefitAdapter;
import app.com.baoviet.adapter.CustomExpandableListBenefitProcessAdapter;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.entity.ClaimInfoDTO;
import app.com.baoviet.entity.ClaimPaymentDTO;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.CalculateDimension;
import app.com.baoviet.utility.StringUtil;

public class TabContractBenefitFragment extends Fragment implements AsyncResponse, DialogEventListener {

    private ExpandableListView exListBenefitProcess;
    private ExpandableListView exListBenefit;
    private TextView tvContractBenefitError;
    private TextView tvContractBenefitProError;
    private TextView tvContractBenefitProNoData;
    private TextView tvContractBenefitNoData;

    private String accountNumber;
    private List<ClaimPaymentDTO> listClaimPayment = new ArrayList<ClaimPaymentDTO>();
    private List<ClaimInfoDTO> listClaimInfor = new ArrayList<ClaimInfoDTO>();
    private CustomExpandableListBenefitAdapter mExpandableListBenefitAdapter;
    private CustomExpandableListBenefitProcessAdapter mExpandableListBenefitProcessAdapter;

    private String typeAccount;
    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_contract_benefit, container, false);
        initInterface(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            accountNumber = bundle.getString(Keys.KEY_ACCOUNT_NUMBER);
            typeAccount = bundle.getString(Keys.KEY_TYPE_ACCOUNT);
        }
        getInitData(Constant.URL_GET_BENEFIT_RESOLVE_PAID);
        getInitData(Constant.URL_GET_BENEFIT_RESOLVE);
        return view;
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getContext());
        alert.dialogEventListener = this;
        exListBenefitProcess = (ExpandableListView) view.findViewById(R.id.exListBenefitProcess);
        exListBenefit = (ExpandableListView) view.findViewById(R.id.exListBenefit);
        tvContractBenefitError = (TextView) view.findViewById(R.id.tvContractBenefitError);
        tvContractBenefitProError = (TextView) view.findViewById(R.id.tvContractBenefitProError);
        tvContractBenefitProNoData = (TextView) view.findViewById(R.id.tvContractBenefitProNoData);
        tvContractBenefitNoData = (TextView) view.findViewById(R.id.tvContractBenefitNoData);

    }

    public void getInitData(String urlApi) {
        try {
            if (accountNumber != null) {
                DataSourceConnection connection;
                Activity activity = getActivity();
                if (isAdded() && activity != null) {
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

    private void addDrawerItems(String urlApi) {
        if (urlApi.equals(Constant.URL_GET_BENEFIT_RESOLVE_PAID)) {
            mExpandableListBenefitAdapter = new CustomExpandableListBenefitAdapter(getContext(), listClaimPayment);
            exListBenefit.setAdapter(mExpandableListBenefitAdapter);
            //set height default of list view
            CalculateDimension.setHeightDefaultOfExpandableListView(exListBenefit);
            exListBenefit.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {

                }
            });

            exListBenefit.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                @Override
                public void onGroupCollapse(int groupPosition) {
                }
            });
            exListBenefit.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                    CalculateDimension.setListViewHeight(expandableListView, groupPosition);
                    return false;
                }
            });

            exListBenefit.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    return false;
                }
            });
        } else if (urlApi.equals(Constant.URL_GET_BENEFIT_RESOLVE)) {
            mExpandableListBenefitProcessAdapter = new CustomExpandableListBenefitProcessAdapter(getContext(), listClaimInfor);
            exListBenefitProcess.setAdapter(mExpandableListBenefitProcessAdapter);
            //set height default of list view
            CalculateDimension.setHeightDefaultOfExpandableListView(exListBenefitProcess);
            exListBenefitProcess.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {

                }
            });

            exListBenefitProcess.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                @Override
                public void onGroupCollapse(int groupPosition) {
                }
            });
            exListBenefitProcess.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                    CalculateDimension.setListViewHeight(expandableListView, groupPosition);
                    return false;
                }
            });

            exListBenefitProcess.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    return false;
                }
            });
        }

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
                if (urlApi.equals(Constant.URL_GET_BENEFIT_RESOLVE_PAID)) {
                    if (Constant.INT_200 == responseStatus) {
                        exListBenefit.setVisibility(View.VISIBLE);
                        tvContractBenefitError.setVisibility(View.GONE);
                        tvContractBenefitNoData.setVisibility(View.GONE);
                        JSONArray arrResult = jsonObject.getJSONArray(Keys.KEY_OBJECT);
                        Gson gson = new GsonBuilder().create();
                        Type listType = new TypeToken<List<ClaimPaymentDTO>>() {
                        }.getType();
                        List<ClaimPaymentDTO> myModelList = gson.fromJson(arrResult.toString(), listType);
                        if (myModelList == null || Constant.INT_0 == myModelList.size()) {
                            tvContractBenefitError.setVisibility(View.GONE);
                            tvContractBenefitNoData.setVisibility(View.VISIBLE);
                            exListBenefit.setVisibility(View.GONE);
                        } else {
                            tvContractBenefitError.setVisibility(View.GONE);
                            tvContractBenefitNoData.setVisibility(View.GONE);
                            exListBenefit.setVisibility(View.VISIBLE);
                            listClaimPayment = myModelList;
                            //show data
                            addDrawerItems(urlApi);
                        }

                    } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                        tvContractBenefitError.setVisibility(View.VISIBLE);
                        tvContractBenefitNoData.setVisibility(View.GONE);
                        exListBenefit.setVisibility(View.GONE);
                        tvContractBenefitError.setText(responseMessage);
                    } else {
                        tvContractBenefitError.setVisibility(View.GONE);
                        tvContractBenefitNoData.setVisibility(View.VISIBLE);
                        exListBenefit.setVisibility(View.GONE);
                    }
                } else if (urlApi.equals(Constant.URL_GET_BENEFIT_RESOLVE)) {
                    if (Constant.INT_200 == responseStatus) {
                        exListBenefitProcess.setVisibility(View.VISIBLE);
                        tvContractBenefitProError.setVisibility(View.GONE);
                        tvContractBenefitProNoData.setVisibility(View.GONE);
                        JSONArray arrResult = jsonObject.getJSONArray(Keys.KEY_OBJECT);
                        Gson gson = new GsonBuilder().create();
                        Type listType = new TypeToken<List<ClaimInfoDTO>>() {
                        }.getType();
                        List<ClaimInfoDTO> myModelList = gson.fromJson(arrResult.toString(), listType);
                        if (myModelList == null || Constant.INT_0 == myModelList.size()) {
                            tvContractBenefitProError.setVisibility(View.GONE);
                            tvContractBenefitProNoData.setVisibility(View.VISIBLE);
                            exListBenefitProcess.setVisibility(View.GONE);
                        } else {
                            tvContractBenefitProError.setVisibility(View.GONE);
                            tvContractBenefitProNoData.setVisibility(View.GONE);
                            exListBenefitProcess.setVisibility(View.VISIBLE);
                            listClaimInfor = myModelList;
                            //show data
                            addDrawerItems(urlApi);
                        }

                    } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                        tvContractBenefitProError.setVisibility(View.VISIBLE);
                        tvContractBenefitProNoData.setVisibility(View.GONE);
                        exListBenefitProcess.setVisibility(View.GONE);
                        tvContractBenefitProError.setText(responseMessage);
                    } else {
                        tvContractBenefitProError.setVisibility(View.GONE);
                        tvContractBenefitProNoData.setVisibility(View.VISIBLE);
                        exListBenefitProcess.setVisibility(View.GONE);
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
    }

}