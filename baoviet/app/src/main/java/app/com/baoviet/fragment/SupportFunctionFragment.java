package app.com.baoviet.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;

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
import app.com.baoviet.utility.Validate;

public class SupportFunctionFragment extends Fragment implements View.OnClickListener, AsyncResponse, DialogEventListener {

    private EditText edtSupportFunctionUsername;
    private TextView tvSupportFunctionUsernameError;
    private Button btnSupportFunctionSearch;
    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;
    String username = Constant.EMPTY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support_function, container, false);
        // initial view
        initInterface(view);
        return view;
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getActivity());
        alert.dialogEventListener = this;
        edtSupportFunctionUsername = (EditText) view.findViewById(R.id.edtSupportFunctionUsername);
        tvSupportFunctionUsernameError = (TextView) view.findViewById(R.id.tvSupportFunctionUsernameError);
        btnSupportFunctionSearch = (Button) view.findViewById(R.id.btnSupportFunctionSearch);
        btnSupportFunctionSearch.setOnClickListener(this);
    }

    public void searchInforUser(String username) {
        try {
            DataSourceConnection connection;
            Activity activity = getActivity();
            if (isAdded() && activity != null) {
                connection = new DataSourceConnection(activity, Constant.URL_GET_SWITCH_USER_MODE, username, Constant.METHOD_POST, true, true);
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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSupportFunctionSearch:
                // Contract Number
                boolean isNotEmptUsername = Validate.validateForm(getActivity(), edtSupportFunctionUsername, tvSupportFunctionUsernameError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_support_function_username_empty));

                if (isNotEmptUsername) {
                    username = edtSupportFunctionUsername.getText().toString();
                    searchInforUser(username);
                } else {
                }
                break;
            default:
                break;
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
                } else if (Constant.ERROR_NETWORK.equals(result)) {
                    message = getResources().getString(R.string.message_error_network);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                } else {
                    JSONObject jsonObject = new JSONObject(result);
                    int responseStatus = jsonObject.getInt(Keys.KEY_RESPONSE_STATUS);
                    String responseMessage = jsonObject.getString(Keys.KEY_RESPONSE_MESSAGE);
                    if (Constant.RESPONSE_CODE_200 == responseStatus) {
                        JSONObject objResult = jsonObject.getJSONObject(Keys.KEY_OBJECT);
                        Gson gson = new GsonBuilder().create();
                        CustomObjectDTO userResult = gson.fromJson(objResult.toString(), CustomObjectDTO.class);
                        List<String> listAccount = userResult.getAccountList();
                        if (listAccount != null && listAccount.size() > Constant.INT_0) {
                            Constant.USER_CODE_FOR_SEARCH = username;
                            Bundle bundle = new Bundle();
                            bundle.putString(Keys.KEY_USER_CODE, username);
                            Fragment fr = new ContractAccountInvidualFragment();
                            fr.setArguments(bundle);
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.frameContent, fr);
                            fragmentTransaction.commit();
                        } else {
                            message = getResources().getString(R.string.txt_support_function_not_find_username);
                            typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
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