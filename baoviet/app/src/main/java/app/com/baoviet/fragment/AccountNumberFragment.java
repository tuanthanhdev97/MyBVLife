package app.com.baoviet.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import app.com.baoviet.ContractActivity;
import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.entity.CustomObjectDTO;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.StringUtil;

public class AccountNumberFragment extends Fragment implements AdapterView.OnItemSelectedListener, AsyncResponse, DialogEventListener {


    private Spinner spContractNumber;
    private TextView tvContractLastestUpdate;

    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;
    List<String> listAccount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_number, container, false);
        initInterface(view);
        getAccountList();
        return view;
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getContext());
        alert.dialogEventListener = this;
        spContractNumber = (Spinner) view.findViewById(R.id.spContractNumber);
        tvContractLastestUpdate = (TextView) view.findViewById(R.id.tvContractLastestUpdate);
        spContractNumber.setOnItemSelectedListener(this);
    }


    public void getAccountList() {
        try {
            Activity activity = getActivity();
            if (isAdded() && activity != null) {
                DataSourceConnection connection;
                connection = new DataSourceConnection(activity, Constant.URL_GET_SWITCH_USER_MODE, Constant.USER_CODE, Constant.METHOD_POST);
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
                    if (listAccount == null) {
                        message = responseMessage;
                        typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                    } else {
                        if (Constant.INT_0 == listAccount.size()) {
                            message = responseMessage;
                            typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                        } else {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_layout, listAccount);
                            adapter.setDropDownViewResource(R.layout.spinner_layout_popup);
                            spContractNumber.setAdapter(adapter);
                            // spinner contract number
                        }
                    }
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        ((ContractActivity) getActivity()).setAccountNumber(listAccount.get(position));
//        ((ContractActivity) getActivity()).navigationFragment();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
