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
import java.util.ArrayList;
import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.adapter.CustomExpandableListInforAccountContractAdapter;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.entity.UserDTO;
import app.com.baoviet.entity.UsersAcctRltnshpDTO;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.CalculateDimension;
import app.com.baoviet.utility.StringUtil;

public class InforAccountFragment extends Fragment implements AsyncResponse, DialogEventListener, View.OnTouchListener {
    private EditText edtInforAccountNumber;
    private EditText edtInforAccountFullName;
    private EditText edtInforAccountTypeAccount;
    private EditText edtInforAccountPhoneNumber;
    private EditText edtInforAccountEmail;
    private EditText edtInforAccountAddress;
    private EditText edtInforAccountTypeDoc;
    private EditText edtInforAccountNumberDoc;
    private LinearLayout lnInforAccountDetail;
    private LinearLayout lnInforAccountContract;
    private TextView tvInforAccountNodataDetail;
    private TextView tvInforAccountNodataContract;
    private ExpandableListView exListInforAccountContract;

    private CustomExpandableListInforAccountContractAdapter mExpandableListInforAccountAdapter;
    private List<UsersAcctRltnshpDTO> listUser = new ArrayList<UsersAcctRltnshpDTO>();
    private String userCode = Constant.EMPTY;
    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_infor_account, container, false);
        // initial view
        initInterface(view);
        //get data bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            userCode = bundle.getString(Keys.KEY_USER_CODE);
        }
        //show data initial
        getAccountInfor();
        getUserInfor();
        return view;
    }

    public void getAccountInfor() {
        try {
            DataSourceConnection connection;
            Activity activity = getActivity();
            if (isAdded() && activity != null) {
                connection = new DataSourceConnection(activity, Constant.URL_GET_ACCOUNT_INFOR, userCode, Constant.METHOD_POST);
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

    public void getUserInfor() {
        try {
            DataSourceConnection connection;
            Activity activity = getActivity();
            if (isAdded() && activity != null) {
                connection = new DataSourceConnection(activity, Constant.URL_GET_USER_INFOR, userCode, Constant.METHOD_POST);
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

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getActivity());
        alert.dialogEventListener = this;
        edtInforAccountNumber = (EditText) view.findViewById(R.id.edtInforAccountNumber);
        edtInforAccountFullName = (EditText) view.findViewById(R.id.edtInforAccountFullName);
        edtInforAccountTypeAccount = (EditText) view.findViewById(R.id.edtInforAccountTypeAccount);
        edtInforAccountPhoneNumber = (EditText) view.findViewById(R.id.edtInforAccountPhoneNumber);
        edtInforAccountEmail = (EditText) view.findViewById(R.id.edtInforAccountEmail);
        edtInforAccountAddress = (EditText) view.findViewById(R.id.edtInforAccountAddress);
        edtInforAccountTypeDoc = (EditText) view.findViewById(R.id.edtInforAccountTypeDoc);
        edtInforAccountNumberDoc = (EditText) view.findViewById(R.id.edtInforAccountNumberDoc);
        lnInforAccountDetail = (LinearLayout) view.findViewById(R.id.lnInforAccountDetail);
        lnInforAccountContract = (LinearLayout) view.findViewById(R.id.lnInforAccountContract);
        tvInforAccountNodataDetail = (TextView) view.findViewById(R.id.tvInforAccountNodataDetail);
        tvInforAccountNodataContract = (TextView) view.findViewById(R.id.tvInforAccountNodataContract);
        exListInforAccountContract = (ExpandableListView) view.findViewById(R.id.exListInforAccountContract);
        edtInforAccountAddress.setOnTouchListener(this);
    }

    public void showAccountInfor(UserDTO userDTO) {
        edtInforAccountNumber.setText(userDTO.getUserCode());
        edtInforAccountFullName.setText(userDTO.getFullName());
        edtInforAccountTypeAccount.setText(StringUtil.getUserTypeFromCode(userDTO.getUserTypeCode()));
        edtInforAccountPhoneNumber.setText(userDTO.getMobifone());
        edtInforAccountEmail.setText(userDTO.getEmail());
        edtInforAccountAddress.setText(userDTO.getAddress());
        edtInforAccountTypeDoc.setText(StringUtil.getClientIdentityDoctypeFromCode(userDTO.getClientIdentityDoctype()));
        edtInforAccountNumberDoc.setText(userDTO.getUserClientIdnumber());
    }

    private void addDrawerItems() {
        mExpandableListInforAccountAdapter = new CustomExpandableListInforAccountContractAdapter(getActivity(), listUser);
        exListInforAccountContract.setAdapter(mExpandableListInforAccountAdapter);
        //set height default of list view
        CalculateDimension.setHeightDefaultOfExpandableListView(exListInforAccountContract);
        exListInforAccountContract.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        exListInforAccountContract.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
        exListInforAccountContract.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {

                CalculateDimension.setListViewHeight(expandableListView, groupPosition);
                return false;
            }
        });

        exListInforAccountContract.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
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
                String resultMessage = jsonObject.getString(Keys.KEY_RESPONSE_MESSAGE);
                if (Constant.URL_GET_ACCOUNT_INFOR.equals(urlApi)) {
                    if (Constant.RESPONSE_CODE_200 == responseStatus) {
                        JSONArray arrResult = jsonObject.getJSONArray(Keys.KEY_OBJECT);
                        Gson gson = new GsonBuilder().create();
                        Type listType = new TypeToken<List<UserDTO>>() {
                        }.getType();
                        List<UserDTO> myModelList = gson.fromJson(arrResult.toString(), listType);
                        if (myModelList == null || Constant.INT_0 == myModelList.size()) {
                            lnInforAccountDetail.setVisibility(View.GONE);
                            tvInforAccountNodataDetail.setVisibility(View.VISIBLE);
                            tvInforAccountNodataDetail.setText(getResources().getString(R.string.txt_infor_account_nodata));
                        } else {
                            lnInforAccountDetail.setVisibility(View.VISIBLE);
                            tvInforAccountNodataDetail.setVisibility(View.GONE);
                            UserDTO userResult = myModelList.get(0);
                            //show Account Infor
                            showAccountInfor(userResult);
                        }
                    } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                        lnInforAccountDetail.setVisibility(View.GONE);
                        tvInforAccountNodataDetail.setVisibility(View.VISIBLE);
                        tvInforAccountNodataDetail.setText(resultMessage);
                    } else {
                        lnInforAccountDetail.setVisibility(View.GONE);
                        tvInforAccountNodataDetail.setVisibility(View.VISIBLE);
                        tvInforAccountNodataDetail.setText(getResources().getString(R.string.txt_infor_account_nodata));
                    }
                } else if (Constant.URL_GET_USER_INFOR.equals(urlApi)) {
                    if (Constant.RESPONSE_CODE_200 == responseStatus) {
                        JSONArray arrResult = jsonObject.getJSONArray(Keys.KEY_OBJECT);
                        Gson gson = new GsonBuilder().create();
                        Type listType = new TypeToken<List<UsersAcctRltnshpDTO>>() {
                        }.getType();
                        List<UsersAcctRltnshpDTO> myModelList = gson.fromJson(arrResult.toString(), listType);
                        if (myModelList == null || Constant.INT_0 == myModelList.size()) {
                            lnInforAccountContract.setVisibility(View.GONE);
                            tvInforAccountNodataContract.setVisibility(View.VISIBLE);
                            tvInforAccountNodataContract.setText(getResources().getString(R.string.txt_infor_account_nodata));
                        } else {
                            lnInforAccountContract.setVisibility(View.VISIBLE);
                            tvInforAccountNodataContract.setVisibility(View.GONE);
                            listUser = myModelList;
                            addDrawerItems();
                        }
                    } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                        lnInforAccountContract.setVisibility(View.GONE);
                        tvInforAccountNodataContract.setVisibility(View.VISIBLE);
                        tvInforAccountNodataContract.setText(resultMessage);
                    } else {
                        lnInforAccountContract.setVisibility(View.GONE);
                        tvInforAccountNodataContract.setVisibility(View.VISIBLE);
                        tvInforAccountNodataContract.setText(getResources().getString(R.string.txt_infor_account_nodata));
                    }
                }
            } else {
//                lnInforAccountDetail.setVisibility(View.GONE);
//                tvInforAccountNodataDetail.setVisibility(View.VISIBLE);
//                tvInforAccountNodataDetail.setText(getResources().getString(R.string.txt_infor_account_nodata));
//                lnInforAccountContract.setVisibility(View.GONE);
//                tvInforAccountNodataContract.setVisibility(View.VISIBLE);
//                tvInforAccountNodataContract.setText(getResources().getString(R.string.txt_infor_account_nodata));
                message = getResources().getString(R.string.message_error_system);
                typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
//            lnInforAccountDetail.setVisibility(View.GONE);
//            tvInforAccountNodataDetail.setVisibility(View.VISIBLE);
//            tvInforAccountNodataDetail.setText(getResources().getString(R.string.txt_infor_account_nodata));
//            lnInforAccountContract.setVisibility(View.GONE);
//            tvInforAccountNodataContract.setVisibility(View.VISIBLE);
//            tvInforAccountNodataContract.setText(getResources().getString(R.string.txt_infor_account_nodata));
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
            case R.id.edtInforAccountAddress:
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popupView = layoutInflater.inflate(R.layout.popup_text, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        TextView tvContentPopup = (TextView) popupView.findViewById(R.id.tvContentPopup);

                        tvContentPopup.setText(edtInforAccountAddress.getText().toString());
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