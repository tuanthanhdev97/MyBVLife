package app.com.baoviet.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import app.com.baoviet.LoginActivity;
import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.implement.GenericTextWatcher;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.StringUtil;
import app.com.baoviet.utility.Validate;

public class UpdatePasswordFragment extends Fragment implements View.OnClickListener, AsyncResponse, DialogEventListener {

    private EditText edtUpdatePasswordNewPassword;
    private EditText edtUpdatePasswordConfirmNewPassword;
    private TextView tvUpdatePasswordNewPasswordError;
    private TextView tvUpdatePasswordConfirmNewPasswordError;
    private TextView tvUpdatePasswordGoToLogin;
    private Button btnChangePassword;
    private String userCode;
    private String passwordOld;

    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_password, container, false);
        // initial view
        initInterface(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            userCode = bundle.getString(Keys.KEY_USER_CODE);
            passwordOld = bundle.getString(Keys.KEY_OLD_PASSWORD);
        }
        // event on change edit text
        onChangeValueEditText();

        // event click button update
        btnChangePassword.setOnClickListener(this);
        tvUpdatePasswordGoToLogin.setOnClickListener(this);

        return view;
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getActivity());
        alert.dialogEventListener = this;
        edtUpdatePasswordNewPassword = (EditText) view.findViewById(R.id.edtUpdatePasswordNewPassword);
        edtUpdatePasswordConfirmNewPassword = (EditText) view.findViewById(R.id.edtUpdatePasswordConfirmNewPassword);
        tvUpdatePasswordNewPasswordError = (TextView) view.findViewById(R.id.tvUpdatePasswordNewPasswordError);
        tvUpdatePasswordConfirmNewPasswordError = (TextView) view.findViewById(R.id.tvUpdatePasswordConfirmNewPasswordError);
        tvUpdatePasswordGoToLogin = (TextView) view.findViewById(R.id.tvUpdatePasswordGoToLogin);
        btnChangePassword = (Button) view.findViewById(R.id.btnChangePassword);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChangePassword:
                // New Password
                boolean isNotEmptyNewPassword = Validate.validateForm(getActivity(), edtUpdatePasswordNewPassword, tvUpdatePasswordNewPasswordError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_update_account_empty_new_password));
                // Confirm New Password
                boolean isNotEmptyConfirmNewPassword = Validate.validateForm(getActivity(), edtUpdatePasswordConfirmNewPassword, tvUpdatePasswordConfirmNewPasswordError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_update_account_empty_confirm_new_password));
                // Validate format password
                boolean isWrongFormatPassword = Validate.validateForm(getActivity(), edtUpdatePasswordNewPassword, tvUpdatePasswordNewPasswordError, Constant.VALIDATE_PATTERN_PASSWORD, getResources().getString(R.string.txt_update_account_wrong_format_password));
                // Validate confirm new password not same
                boolean isNotSame = Validate.validateMappingValue(getActivity(), edtUpdatePasswordNewPassword, edtUpdatePasswordConfirmNewPassword, tvUpdatePasswordConfirmNewPasswordError, Constant.VALIDATE_PATTERN_CONFIRM_NEW_PASSWORD, getResources().getString(R.string.txt_update_account_wrong_confirm_new_password));

                if (isNotEmptyNewPassword && isNotEmptyConfirmNewPassword
                        && isNotSame && isWrongFormatPassword) {
                    updatePassword();
                }
                break;
            case R.id.tvUpdatePasswordGoToLogin:
                Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            default:
                break;
        }

    }

    // validate Empty value in edittext
    public void onChangeValueEditText() {
        edtUpdatePasswordNewPassword.addTextChangedListener(
                new GenericTextWatcher(getActivity(), edtUpdatePasswordNewPassword, tvUpdatePasswordNewPasswordError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_update_account_empty_new_password)));
        edtUpdatePasswordConfirmNewPassword.addTextChangedListener(
                new GenericTextWatcher(getActivity(), edtUpdatePasswordConfirmNewPassword, tvUpdatePasswordConfirmNewPasswordError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_update_account_empty_confirm_new_password)));
        edtUpdatePasswordConfirmNewPassword.addTextChangedListener(
                new GenericTextWatcher(getActivity(), edtUpdatePasswordNewPassword, edtUpdatePasswordConfirmNewPassword, tvUpdatePasswordConfirmNewPasswordError, Constant.VALIDATE_PATTERN_CONFIRM_NEW_PASSWORD, getResources().getString(R.string.txt_update_account_wrong_confirm_new_password)));
    }

    public void updatePassword() {
        try {
            JSONObject postDataParams = new JSONObject();
            postDataParams.put(Keys.KEY_USERNAME, this.userCode);
            postDataParams.put(Keys.KEY_PASSWORD, this.passwordOld);
            postDataParams.put(Keys.KEY_NEW_PASSWORD, edtUpdatePasswordNewPassword.getText().toString());
            String inputParams = postDataParams.toString().replace("\\", "").replace("\"[", "[").replace("]\"", "]");
            Activity activity = getActivity();
            if (isAdded() && activity != null) {
                DataSourceConnection connection = new DataSourceConnection(activity, Constant.URL_UPDATE_PASSWORD, inputParams, Constant.METHOD_POST, true, true);
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
                } else if (Constant.ERROR_NETWORK.equals(result)) {
                    message = getResources().getString(R.string.message_error_network);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                } else {
                    JSONObject jsonObject = new JSONObject(result);
                    int responseStatus = jsonObject.getInt(Keys.KEY_RESPONSE_STATUS);
                    String responseMessage = jsonObject.getString(Keys.KEY_RESPONSE_MESSAGE);
                    if (Constant.RESPONSE_CODE_200 == responseStatus) {
                        message = getResources().getString(R.string.txt_update_account_password_success);
                        typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_BACK, true);
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
        if (isClickOk) {
            switch (actionDialog) {
                case Constant.ACTION_DIALOG_BACK:
                    Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
                    intentLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentLogin);
                    getActivity().finish();
                    break;
            }
        }
    }
}