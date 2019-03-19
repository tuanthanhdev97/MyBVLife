package app.com.baoviet.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.adapter.ContractAccountGroupAdapter;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.entity.CustomObjectDTO;
import app.com.baoviet.entity.GroupInfoDTO;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.StringUtil;

public class ContractAccountGrouplFragment extends Fragment implements TabLayout.OnTabSelectedListener, AsyncResponse, DialogEventListener, View.OnClickListener {
    private Spinner spGroupContracNumber;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tvGroupLastTimeUpdate;
    private String values;
    private ContractAccountGroupAdapter adapterTabLayout;

    private ImageView imgGroupExpandInforContract;
    private RelativeLayout rlGroupExpandInforContract;
    private EditText edtGroupReleaseDate;
    private EditText edtGroupEffectiveDate;
    private EditText edtGroupProduct;
    private EditText edtGroupType;
    private EditText edtGroupStatus;
    private TextView tvGroupProductName;

    private boolean isExpand = true;
    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;
    List<String> listAccount;
    String usercode = Constant.EMPTY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contract_account_group, container, false);
        // initial view
        initInterface(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            usercode = bundle.getString(Keys.KEY_USER_CODE);
            listAccount = bundle.getStringArrayList(Keys.KEY_LIST_ACCOUNT_NUMBER);
            if (!StringUtil.isNullOrEmpty(usercode)) {
                //get account list
                getAccountList();
            } else if (listAccount != null) {
                setViews();
            }
            //get group infor
            getGroupInfor();

        }
        return view;
    }

    //get group infor
    public void getGroupInfor() {
        try {
            DataSourceConnection connection;
            Activity activity = getActivity();
            if (isAdded() && activity != null) {
                connection = new DataSourceConnection(activity, Constant.URL_POST_GROUP_INFOR, getValues(), Constant.METHOD_POST);
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

    public void showInforGroup(GroupInfoDTO groupInfoDTO) {
        edtGroupReleaseDate.setText(StringUtil.formatDateDDMMYYFromDate(groupInfoDTO.getGrpProcessedDate(), Constant.DDMMYY));
        edtGroupEffectiveDate.setText(StringUtil.formatDateDDMMYYFromDate(groupInfoDTO.getGrpEffectiveDate(), Constant.DDMMYY));
        if (StringUtil.isNullOrEmpty(groupInfoDTO.getGrpProductName())) {
            tvGroupProductName.setText(getResources().getString(R.string.txt_contract_account_group_product_code));
            edtGroupProduct.setText(groupInfoDTO.getGrpProductCode());
        } else {
            tvGroupProductName.setText(getResources().getString(R.string.txt_contract_account_group_product));
            edtGroupProduct.setText(groupInfoDTO.getGrpProductName());
        }
        edtGroupType.setText(groupInfoDTO.getGrpProductTypeDescription());
        edtGroupStatus.setText(groupInfoDTO.getGrpStatusDescription());
        // add tab layout
        addTabLayout();
//        addTabToAdapter();

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public void setTextLastTimeUpdate(String value) {
        tvGroupLastTimeUpdate.setText(value);
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getActivity());
        alert.dialogEventListener = this;
        spGroupContracNumber = (Spinner) view.findViewById(R.id.spGroupContracNumber);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_group);
        viewPager = (ViewPager) view.findViewById(R.id.viewPagerGroup);
        tvGroupLastTimeUpdate = (TextView) view.findViewById(R.id.tvGroupLastTimeUpdate);

        edtGroupReleaseDate = (EditText) view.findViewById(R.id.edtGroupReleaseDate);
        edtGroupEffectiveDate = (EditText) view.findViewById(R.id.edtGroupEffectiveDate);
        edtGroupProduct = (EditText) view.findViewById(R.id.edtGroupProduct);
        edtGroupType = (EditText) view.findViewById(R.id.edtGroupType);
        edtGroupStatus = (EditText) view.findViewById(R.id.edtGroupStatus);
        imgGroupExpandInforContract = (ImageView) view.findViewById(R.id.imgGroupExpandInforContract);
        imgGroupExpandInforContract.setOnClickListener(this);
        rlGroupExpandInforContract = (RelativeLayout) view.findViewById(R.id.rlGroupExpandInforContract);
        tvGroupProductName = (TextView) view.findViewById(R.id.tvGroupProductName);
        spGroupContracNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                setValues(listAccount.get(position));
                addTabToAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addTabLayout() {
        if (Constant.INT_0 == tabLayout.getTabCount()) {
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_group_contract_member));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_group_payment_process));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_group_cost));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_group_payment_benefit));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_group_value_account));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_group_benefit));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_group_handle_benefit));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_group_ebill));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_group_notice));
            tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        }
    }

    public void addTabToAdapter() {
        adapterTabLayout = new ContractAccountGroupAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount(), getValues(), getActivity());
        viewPager.setAdapter(adapterTabLayout);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(this);
    }

    public void getAccountList() {
        try {
            DataSourceConnection connection;
            Activity activity = getActivity();
            if (isAdded() && activity != null) {
                connection = new DataSourceConnection(activity, Constant.URL_GET_SWITCH_USER_MODE, usercode, Constant.METHOD_POST);
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

    public void setViews() {
        setValues(listAccount.get(0));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_layout, listAccount);
        adapter.setDropDownViewResource(R.layout.spinner_layout_popup);
        spGroupContracNumber.setAdapter(adapter);

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
                if (urlApi.equals(Constant.URL_GET_SWITCH_USER_MODE)) {
                    if (Constant.RESPONSE_CODE_200 == responseStatus) {
                        JSONObject objResult = jsonObject.getJSONObject(Keys.KEY_OBJECT);
                        Gson gson = new GsonBuilder().create();
                        CustomObjectDTO userResult = gson.fromJson(objResult.toString(), CustomObjectDTO.class);
                        String typeMenu = Constant.EMPTY;
                        boolean isInvidual = userResult.isAccount();
                        boolean isGroup = userResult.isGroup();
                        if (isInvidual && !isGroup) {
                            typeMenu = Constant.TYPE_CONTRACT_IS_INVIDUAL;
                        } else if (!isInvidual && isGroup) {
                            typeMenu = Constant.TYPE_CONTRACT_IS_GROUP;
                        } else {
                            typeMenu = Constant.TYPE_CONTRACT_INVISIBLE;
                        }
                        listAccount = userResult.getAccountList();
                        // spinner contract number
                        setViews();

                    } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                        message = responseMessage;
                        typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                    } else {
                        message = getResources().getString(R.string.message_error_system);
                        typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                    }
                } else if (urlApi.equals(Constant.URL_POST_GROUP_INFOR)) {
                    if (Constant.RESPONSE_CODE_200 == responseStatus) {
                        JSONObject objResult = jsonObject.getJSONObject(Keys.KEY_OBJECT);
                        Gson gson = new GsonBuilder().create();
                        GroupInfoDTO groupInfor = gson.fromJson(objResult.toString(), GroupInfoDTO.class);
                        showInforGroup(groupInfor);

                    } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                        message = responseMessage;
                        typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                    } else {
                        message = getResources().getString(R.string.message_error_system);
                        typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgGroupExpandInforContract:
                isExpand = !isExpand;
                if (isExpand) {
                    rlGroupExpandInforContract.setVisibility(View.VISIBLE);
                    imgGroupExpandInforContract.setImageResource(R.drawable.ic_bullet_toggle_minus);
                } else {
                    rlGroupExpandInforContract.setVisibility(View.GONE);
                    imgGroupExpandInforContract.setImageResource(R.drawable.ic_bullet_toggle_plus);

                }

        }
    }
}