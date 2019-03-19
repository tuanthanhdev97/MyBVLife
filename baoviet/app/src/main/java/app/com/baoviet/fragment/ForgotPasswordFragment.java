package app.com.baoviet.fragment;

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

public class ForgotPasswordFragment extends Fragment implements AsyncResponse, View.OnClickListener, DialogEventListener {

    private EditText edtForgotPassAccount;
    private EditText edtForgotPassFullName;
    private EditText edtForgotPassIdentify;
    private TextView tvForgotPassFullNameError;
    private TextView tvForgotPassIdentifyError;
    private Button btnSendPassword;

    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        // initial view
        initInterface(view);
        // event onchange text in edittext
        onChangeValueEditText();
        return view;
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getActivity());
        alert.dialogEventListener = this;
        edtForgotPassAccount = (EditText) view.findViewById(R.id.edtForgotPassAccount);
        edtForgotPassFullName = (EditText) view.findViewById(R.id.edtForgotPassFullName);
        edtForgotPassIdentify = (EditText) view.findViewById(R.id.edtForgotPassIdentify);
        tvForgotPassFullNameError = (TextView) view.findViewById(R.id.tvForgotPassFullNameError);
        tvForgotPassIdentifyError = (TextView) view.findViewById(R.id.tvForgotPassIdentifyError);
        btnSendPassword = (Button) view.findViewById(R.id.btnSendPassword);
        btnSendPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSendPassword:
                // FullName
                boolean isEmptyFullName = Validate.validateForm(getActivity(), edtForgotPassFullName, tvForgotPassFullNameError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_forgot_password_fullname_empty));
                boolean isEmptyIdentify = Validate.validateForm(getActivity(), edtForgotPassIdentify, tvForgotPassIdentifyError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_forgot_password_identify_empty));

                if (isEmptyFullName && isEmptyIdentify) {
                    try {
                        JSONObject postDataParams = new JSONObject();
                        postDataParams.put(Keys.KEY_USERNAME, edtForgotPassAccount.getText().toString());
                        postDataParams.put(Keys.KEY_STRNAME, edtForgotPassFullName.getText().toString());
                        postDataParams.put(Keys.KEY_STRID, edtForgotPassIdentify.getText().toString());
                        String inputParams = postDataParams.toString().replace("\\", "").replace("\"[", "[").replace("]\"", "]");

                        DataSourceConnection connection = new DataSourceConnection(getActivity(), Constant.URL_FORGOT_PASSWORD_VALID, inputParams, Constant.METHOD_POST, true, true);
                        connection.delegate = this;
                        connection.execute();


                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage());
                        message = getResources().getString(R.string.message_error_system);
                        typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                    }
                }
                break;

            default:
                break;
        }

    }

    // validate Empty value in edittext
    public void onChangeValueEditText() {
        edtForgotPassFullName.addTextChangedListener(
                new GenericTextWatcher(getActivity(), edtForgotPassFullName, tvForgotPassFullNameError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_forgot_password_fullname_empty)));
        edtForgotPassIdentify.addTextChangedListener(
                new GenericTextWatcher(getActivity(), edtForgotPassIdentify, tvForgotPassIdentifyError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_forgot_password_identify_empty)));
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
                if (urlApi.equals(Constant.URL_FORGOT_PASSWORD_VALID)) {
                    if (Constant.RESPONSE_CODE_220 == responseStatus) {
                        message = responseMessage;
                        typeDialog = Constant.TYPE_ALERT_DIALOG_CONFIRM;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_CONFIRM_FORGOT_PASSWORD, true);
                    } else {
                        message = responseMessage;
                        typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                    }
                } else if (urlApi.equals(Constant.URL_FORGOT_PASSWORD)) {
                    if (Constant.RESPONSE_CODE_200 == responseStatus) {
                        message = getResources().getString(R.string.txt_forgot_password_success);
                        typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                    } else {
                        message = responseMessage;
                        typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
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
            if (Constant.ACTION_DIALOG_CONFIRM_FORGOT_PASSWORD.equals(actionDialog)) {
                try {
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put(Keys.KEY_USERNAME, edtForgotPassAccount.getText().toString());
                    postDataParams.put(Keys.KEY_STRNAME, edtForgotPassFullName.getText().toString());
                    postDataParams.put(Keys.KEY_STRID, edtForgotPassIdentify.getText().toString());
                    String inputParams = postDataParams.toString().replace("\\", "").replace("\"[", "[").replace("]\"", "]");

                    DataSourceConnection connection = new DataSourceConnection(getActivity(), Constant.URL_FORGOT_PASSWORD, inputParams, Constant.METHOD_POST, true, true);
                    connection.delegate = this;
                    connection.execute();


                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                    message = getResources().getString(R.string.message_error_system);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                }
            }
        }
    }
}