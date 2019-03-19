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
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.adapter.ContractAccountInvidualAdapter;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.entity.CustomObjectDTO;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.StringUtil;

public class ContractAccountInvidualFragment extends Fragment implements TabLayout.OnTabSelectedListener, AsyncResponse, DialogEventListener {
    private Spinner spNumberContract;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tvLastTimeUpdate;
    private String values;
    private ContractAccountInvidualAdapter adapterTabLayout;
    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;
    List<String> listAccount;
    String usercode = Constant.EMPTY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contract_account_invidual, container, false);
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

        }
        return view;
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
        tvLastTimeUpdate.setText(value);
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getActivity());
        alert.dialogEventListener = this;
        spNumberContract = (Spinner) view.findViewById(R.id.spContracNumber);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        tvLastTimeUpdate = (TextView) view.findViewById(R.id.tvLastTimeUpdate);
        spNumberContract.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_invidual_tab_infor_general));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_invidual_tab_infor_customer));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_invidual_tab_value));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_invidual_tab_payment_process));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_invidual_tab_fees_and_costs));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_invidual_tab_benefit));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_invidual_tab_bill));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.txt_contract_account_invidual_tab_notice));
            tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        }
    }

    public void addTabToAdapter() {
        adapterTabLayout = new ContractAccountInvidualAdapter
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
        spNumberContract.setAdapter(adapter);
        // add tab layout
        addTabLayout();
        addTabToAdapter();
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