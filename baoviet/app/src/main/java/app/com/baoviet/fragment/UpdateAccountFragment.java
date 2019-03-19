package app.com.baoviet.fragment;

import android.app.Activity;
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

public class UpdateAccountFragment extends Fragment implements View.OnClickListener, AsyncResponse, DialogEventListener {
    private EditText edtUpdateAccountUsername;
    private EditText edtUpdateAccountUsernameOther;
    private EditText edtUpdateAccountOldPassword;
    private EditText edtUpdateAccountNewPassword;
    private EditText edtUpdateAccountConfirmNewPassword;
    private Button btnUpdateUsername;
    private Button btnCancelUpdateUsername;
    private Button btnUpdatePassword;
    private Button btnCancelUpdatePassword;
    private TextView tvUpdateAccountOldPasswordError;
    private TextView tvUpdateAccountNewPasswordError;
    private TextView tvUpdateAccountConfirmNewPasswordError;

    private String userTypeCode = Constant.EMPTY;
    private String username = Constant.EMPTY;
    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_account, container, false);
        // initial view
        initInterface(view);
        //get data bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            username = bundle.getString(Keys.KEY_USERNAME);
            userTypeCode = bundle.getString(Keys.KEY_USER_TYPE_CODE);
        }
        //show data initial
        showDataInitial();
        // event on change edit text
        onChangeValueEditText();
        return view;
    }

    public void showDataInitial() {
        edtUpdateAccountUsername.setText(username);
        btnUpdateUsername.setAlpha(.5f);
        btnCancelUpdateUsername.setAlpha(.5f);
        if (Constant.USER_TYPE_CODE_STAFF.equals(userTypeCode)) {
            edtUpdateAccountUsernameOther.setFocusable(false);
            btnUpdateUsername.setEnabled(false);
            btnCancelUpdateUsername.setEnabled(false);
        } else {
            edtUpdateAccountUsernameOther.setFocusable(true);
            btnUpdateUsername.setEnabled(true);
            btnCancelUpdateUsername.setEnabled(true);
        }
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getActivity());
        alert.dialogEventListener = this;
        edtUpdateAccountUsername = (EditText) view.findViewById(R.id.edtUpdateAccountUsername);
        edtUpdateAccountUsernameOther = (EditText) view.findViewById(R.id.edtUpdateAccountUsernameOther);
        edtUpdateAccountOldPassword = (EditText) view.findViewById(R.id.edtUpdateAccountOldPassword);
        edtUpdateAccountNewPassword = (EditText) view.findViewById(R.id.edtUpdateAccountNewPassword);
        edtUpdateAccountConfirmNewPassword = (EditText) view.findViewById(R.id.edtUpdateAccountConfirmNewPassword);
        tvUpdateAccountOldPasswordError = (TextView) view.findViewById(R.id.tvUpdateAccountOldPasswordError);
        tvUpdateAccountNewPasswordError = (TextView) view.findViewById(R.id.tvUpdateAccountNewPasswordError);
        tvUpdateAccountConfirmNewPasswordError = (TextView) view.findViewById(R.id.tvUpdateAccountConfirmNewPasswordError);
        btnUpdateUsername = (Button) view.findViewById(R.id.btnUpdateUsername);
        btnCancelUpdateUsername = (Button) view.findViewById(R.id.btnCancelUpdateUsername);
        btnUpdatePassword = (Button) view.findViewById(R.id.btnUpdatePassword);
        btnCancelUpdatePassword = (Button) view.findViewById(R.id.btnCancelUpdatePassword);
        btnUpdateUsername.setOnClickListener(this);
        btnCancelUpdateUsername.setOnClickListener(this);
        btnUpdatePassword.setOnClickListener(this);
        btnCancelUpdatePassword.setOnClickListener(this);
    }

    public void updateUsername() {
        try {
            JSONObject postDataParams = new JSONObject();
            postDataParams.put(Keys.KEY_USERNAME, edtUpdateAccountUsername.getText().toString());
            postDataParams.put(Keys.KEY_USERALIAS, edtUpdateAccountUsernameOther.getText().toString());
            String inputParams = postDataParams.toString().replace("\\", "").replace("\"[", "[").replace("]\"", "]");
            Activity activity = getActivity();
            if (isAdded() && activity != null) {
                DataSourceConnection connection = new DataSourceConnection(activity, Constant.URL_UPDATE_USER_ALIAS, inputParams, Constant.METHOD_POST, true, true);
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

    public void updatePassword() {
        try {
            JSONObject postDataParams = new JSONObject();
            postDataParams.put(Keys.KEY_USERNAME, username);
            postDataParams.put(Keys.KEY_PASSWORD, edtUpdateAccountOldPassword.getText().toString());
            postDataParams.put(Keys.KEY_NEW_PASSWORD, edtUpdateAccountNewPassword.getText().toString());
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdateUsername:
                updateUsername();
                break;
            case R.id.btnCancelUpdateUsername:
                edtUpdateAccountUsernameOther.setText(Constant.EMPTY);
                break;
            case R.id.btnCancelUpdatePassword:
                edtUpdateAccountOldPassword.setText(Constant.EMPTY);
                edtUpdateAccountNewPassword.setText(Constant.EMPTY);
                edtUpdateAccountConfirmNewPassword.setText(Constant.EMPTY);
                break;
            case R.id.btnUpdatePassword:
                // Validate Empty Old Password
                boolean isNotEmptyOldPassword = Validate.validateForm(getActivity(), edtUpdateAccountOldPassword, tvUpdateAccountOldPasswordError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_update_account_empty_old_password));
                // New Password
                boolean isNotEmptyNewPassword = Validate.validateForm(getActivity(), edtUpdateAccountNewPassword, tvUpdateAccountNewPasswordError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_update_account_empty_new_password));
                // Confirm New Password
                boolean isNotEmptyConfirmNewPassword = Validate.validateForm(getActivity(), edtUpdateAccountConfirmNewPassword, tvUpdateAccountConfirmNewPasswordError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_update_account_empty_confirm_new_password));
                // Validate new password same old password
                boolean isDuplicatePass = Validate.validateMappingValue(getActivity(), edtUpdateAccountOldPassword, edtUpdateAccountNewPassword, tvUpdateAccountNewPasswordError, Constant.VALIDATE_PATTERN_COMPARE_PASSWORD, getResources().getString(R.string.txt_update_account_duplicate_password));
                // Validate format password
                boolean isWrongFormatPassword = Validate.validateForm(getActivity(), edtUpdateAccountNewPassword, tvUpdateAccountNewPasswordError, Constant.VALIDATE_PATTERN_PASSWORD, getResources().getString(R.string.txt_update_account_wrong_format_password));
                // Validate confirm new password not same
                boolean isNotSame = Validate.validateMappingValue(getActivity(), edtUpdateAccountNewPassword, edtUpdateAccountConfirmNewPassword, tvUpdateAccountConfirmNewPasswordError, Constant.VALIDATE_PATTERN_CONFIRM_NEW_PASSWORD, getResources().getString(R.string.txt_update_account_wrong_confirm_new_password));

                if (isNotEmptyOldPassword && isNotEmptyNewPassword && isNotEmptyConfirmNewPassword
                        && isWrongFormatPassword && isDuplicatePass && isNotSame) {
                    updatePassword();
                }
                break;
        }

    }

    // validate Empty value in edittext
    public void onChangeValueEditText() {
        edtUpdateAccountUsernameOther.addTextChangedListener(
                new GenericTextWatcher(getActivity(), edtUpdateAccountUsernameOther, btnUpdateUsername, Constant.VALIDATE_PATTERN_NULL_EMPTY_BTN));
        edtUpdateAccountUsernameOther.addTextChangedListener(
                new GenericTextWatcher(getActivity(), edtUpdateAccountUsernameOther, btnCancelUpdateUsername, Constant.VALIDATE_PATTERN_NULL_EMPTY_BTN));
        edtUpdateAccountOldPassword.addTextChangedListener(
                new GenericTextWatcher(getActivity(), edtUpdateAccountOldPassword, tvUpdateAccountOldPasswordError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_update_account_empty_old_password)));
        edtUpdateAccountNewPassword.addTextChangedListener(
                new GenericTextWatcher(getActivity(), edtUpdateAccountNewPassword, tvUpdateAccountNewPasswordError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_update_account_empty_new_password)));
        edtUpdateAccountConfirmNewPassword.addTextChangedListener(
                new GenericTextWatcher(getActivity(), edtUpdateAccountConfirmNewPassword, tvUpdateAccountConfirmNewPasswordError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_update_account_empty_confirm_new_password)));
        edtUpdateAccountNewPassword.addTextChangedListener(
                new GenericTextWatcher(getActivity(), edtUpdateAccountOldPassword, edtUpdateAccountNewPassword, tvUpdateAccountNewPasswordError, Constant.VALIDATE_PATTERN_COMPARE_PASSWORD, getResources().getString(R.string.txt_update_account_duplicate_password)));
        edtUpdateAccountConfirmNewPassword.addTextChangedListener(
                new GenericTextWatcher(getActivity(), edtUpdateAccountNewPassword, edtUpdateAccountConfirmNewPassword, tvUpdateAccountConfirmNewPasswordError, Constant.VALIDATE_PATTERN_CONFIRM_NEW_PASSWORD, getResources().getString(R.string.txt_update_account_wrong_confirm_new_password)));
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
                    if (urlApi.equals(Constant.URL_UPDATE_USER_ALIAS)) {
                        if (Constant.RESPONSE_CODE_200 == responseStatus) {
                            message = getResources().getString(R.string.txt_update_account_username_success);
                            typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                        } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                            message = responseMessage;
                            typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                        } else {
                            message = getResources().getString(R.string.message_error_system);
                            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                        }
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                    } else if (urlApi.equals(Constant.URL_UPDATE_PASSWORD)) {
                        if (Constant.RESPONSE_CODE_200 == responseStatus) {
                            message = getResources().getString(R.string.txt_update_account_password_success);
                            typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                        } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                            message = responseMessage;
                            typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                        } else {
                            message = getResources().getString(R.string.message_error_system);
                            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                        }
                    }
                }
            } else {
                message = getResources().getString(R.string.message_error_system);
                typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            message = getResources().getString(R.string.message_error_system);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
        }
        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
    }

    @Override
    public void processFinishStream(ResponseStream inputStream) {

    }

    @Override
    public void OnClickedButtonDialog(boolean isClickOk, String actionDialog, String typeDialog) {
    }
}