package app.com.baoviet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.datasource.SaveSharedPreference;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.entity.UserDTO;
import app.com.baoviet.implement.GenericTextWatcher;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.GrantPermission;
import app.com.baoviet.utility.StringUtil;
import app.com.baoviet.utility.Validate;

public class LoginActivity extends AppCompatActivity implements android.view.View.OnClickListener, AsyncResponse, DialogEventListener {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private Button btnLoginPayment;
    private TextView tvFogrotPassword;
    private TextView tvLoginSignUp;
    private LinearLayout lnLoginPolicy;
    private LinearLayout lnLoginContact;
    private LinearLayout lnLoginTerm;
    private LinearLayout lnLoginChat;
    private LinearLayout lnLoginHotline;
    private TextView tvLoginUsernameError;
    private TextView tvLoginPasswordError;
    private TextView tvLoginTypeDevice;
    //    private ImageView imgLoginNotification;
    private String resultMessage;
    private DataSourceConnection connection;
    private ProgressDialog progressDialog;

    private JSONObject objResult;
    private Gson gson;
    private UserDTO userResult;

    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // initial view
        initInterface();
        // event onchange data in edittext
        onChangeValueEditText();
    }

    // init interface login page
    public void initInterface() {
        alert = new AlertDialogCustom(this);
        alert.dialogEventListener = this;
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        tvLoginUsernameError = (TextView) findViewById(R.id.tvLoginUsernameError);
        tvLoginPasswordError = (TextView) findViewById(R.id.tvLoginPasswordError);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLoginPayment = (Button) findViewById(R.id.btnLoginPayment);
        tvFogrotPassword = (TextView) findViewById(R.id.tvFogrotPassword);
        tvLoginSignUp = (TextView) findViewById(R.id.tvLoginSignUp);
        tvLoginTypeDevice = (TextView) findViewById(R.id.tvLoginTypeDevice);
//            imgLoginNotification = (ImageView) findViewById(R.id.imgLoginNotification);

        String forgotPass = getResources().getString(R.string.txt_forgot_password);
        SpannableString content = new SpannableString(forgotPass);
        content.setSpan(new UnderlineSpan(), 0, forgotPass.length(), 0);

        String signUpStr = getResources().getString(R.string.txt_sign_up);
        SpannableString contentSignUp = new SpannableString(signUpStr);
        contentSignUp.setSpan(new UnderlineSpan(), 0, signUpStr.length(), 0);
        tvFogrotPassword.setText(content);
        tvLoginSignUp.setText(contentSignUp);
        tvFogrotPassword.setText(content);
//        imgLoginPayment.setOnClickListener(this);
//            imgLoginNotification.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnLoginPayment.setOnClickListener(this);
        tvFogrotPassword.setOnClickListener(this);
        tvLoginSignUp.setOnClickListener(this);
        //linerlayout for footer
        if (Constant.TYPE_DEVICE_TABLET.equals(tvLoginTypeDevice.getText())) {
            lnLoginPolicy = (LinearLayout) findViewById(R.id.lnLoginPolicy);
            lnLoginContact = (LinearLayout) findViewById(R.id.lnLoginContact);
            lnLoginTerm = (LinearLayout) findViewById(R.id.lnLoginTerm);
            lnLoginChat = (LinearLayout) findViewById(R.id.lnLoginChat);
            lnLoginHotline = (LinearLayout) findViewById(R.id.lnLoginHotline);
            lnLoginPolicy.setOnClickListener(this);
            lnLoginContact.setOnClickListener(this);
            lnLoginTerm.setOnClickListener(this);
            lnLoginChat.setOnClickListener(this);
            lnLoginHotline.setOnClickListener(this);
        } else if (Constant.TYPE_DEVICE_PHONE.equals(tvLoginTypeDevice.getText())) {

        }
//        String a = SaveSharedPreference.getUserName(this);
//        if (!StringUtil.isNullOrEmpty(a)) {
//            try {
//                JSONObject postDataParams = new JSONObject();
//                postDataParams.put(Keys.KEY_USERNAME, SaveSharedPreference.getUserName(this));
//                postDataParams.put(Keys.KEY_PASSWORD, SaveSharedPreference.getPassword(this));
//                String inputParams = postDataParams.toString().replace("\\", "").replace("\"[", "[").replace("]\"", "]");
//
//                connection = new DataSourceConnection(this, Constant.URL_LOGIN, inputParams, Constant.METHOD_POST, edtUsername.getText().toString(), edtPassword.getText().toString());
//                connection.delegate = this;
//                connection.execute();
//
//            } catch (Exception e) {                        Log.e("ERROR", e.getMessage());
//                message = getResources().getString(R.string.message_error_system);
//                typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
//                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
//            }
//        }
    }

    // on change value edittext
    public void onChangeValueEditText() {
        // Username
        edtUsername.addTextChangedListener(new GenericTextWatcher(this, edtUsername, tvLoginUsernameError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_login_error_username)));
        // Password
        edtPassword.addTextChangedListener(new GenericTextWatcher(this, edtPassword, tvLoginPasswordError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_login_error_password)));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Intent intentFooter = new Intent(getApplicationContext(), FooterActivity.class);
        Bundle bundle;
        switch (v.getId()) {
            case R.id.btnLogin:
                boolean isEmptyUsername = Validate.validateForm(this, edtUsername, tvLoginUsernameError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_login_error_username));
                boolean isEmptyPassword = Validate.validateForm(this, edtPassword, tvLoginPasswordError, Constant.VALIDATE_PATTERN_NULL_OR_EMPTY, getResources().getString(R.string.txt_login_error_password));
                if (isEmptyUsername && isEmptyPassword) {
                    try {
                        btnLogin.setEnabled(false);
                        JSONObject postDataParams = new JSONObject();
                        postDataParams.put(Keys.KEY_USERNAME, edtUsername.getText().toString());
                        postDataParams.put(Keys.KEY_PASSWORD, edtPassword.getText().toString());
                        String inputParams = postDataParams.toString().replace("\\", "").replace("\"[", "[").replace("]\"", "]");

                        connection = new DataSourceConnection(this, Constant.URL_LOGIN, inputParams, Constant.METHOD_POST, edtUsername.getText().toString(), edtPassword.getText().toString());
                        connection.delegate = this;
                        connection.execute();

                    } catch (Exception e) {
                        btnLogin.setEnabled(true);
                        Log.e("ERROR", e.getMessage());
                        message = getResources().getString(R.string.message_error_system);
                        typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                    }
                } else {
                    btnLogin.setEnabled(true);
                }
                break;
            case R.id.lnLoginPolicy:
                bundle = new Bundle();
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_SECURITY);
                intentFooter.putExtras(bundle);
                startActivity(intentFooter);
                break;
            case R.id.lnLoginContact:
                bundle = new Bundle();
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_CONTACT);
                intentFooter.putExtras(bundle);
                startActivity(intentFooter);
                break;
            case R.id.lnLoginTerm:
                bundle = new Bundle();
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_TERMS);
                intentFooter.putExtras(bundle);
                startActivity(intentFooter);
                break;
            case R.id.lnLoginChat:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.CHAT_LINK));
                startActivity(browserIntent);
                break;
            case R.id.lnLoginHotline:
                if (GrantPermission.hasPermissionCallPhone(this)) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:18006966"));
                    startActivity(callIntent);
                }
                break;
            case R.id.btnLoginPayment:
                btnLoginPayment.setEnabled(false);
                intent.putExtra(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_PAYMENT);
                startActivity(intent);
                break;
//            case R.id.imgLoginNotification:
//                intent.putExtra(Keys.KEY_TRANSFER, Constant.TRANSFER_TO_NOTIFICATION_PAGE);
//                startActivity(intent);
//                break;
            case R.id.tvFogrotPassword:
                tvFogrotPassword.setEnabled(false);
                intent.putExtra(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_FORGOT_PASSWORD);
                startActivity(intent);
                break;
            case R.id.tvLoginSignUp:
                tvLoginSignUp.setEnabled(false);
                intent.putExtra(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_REGISTER);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvLoginSignUp.setEnabled(true);
        tvFogrotPassword.setEnabled(true);
        btnLoginPayment.setEnabled(true);
    }

    private void saveTokenNotificationToDb(String tokenNof) {
        try {
            connection = new DataSourceConnection(this, Constant.URL_INSERT_TOKEN_NOTIFICATION, tokenNof, Constant.METHOD_POST);
            connection.delegate = this;
            connection.execute();

        } catch (Exception e) {
            btnLogin.setEnabled(true);
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
                if (Constant.ERROR_SERVER.equals(result) || Constant.ERROR_NETWORK.equals(result)) {
                    btnLogin.setEnabled(true);
                    message = getResources().getString(R.string.message_error_server);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                } else {
                    JSONObject jsonObject = new JSONObject(result);
                    int responseStatus = jsonObject.getInt(Keys.KEY_RESPONSE_STATUS);
                    String responseMessage = jsonObject.getString(Keys.KEY_RESPONSE_MESSAGE);
                    if (Constant.RESPONSE_CODE_200 == responseStatus) {
                        if (Constant.URL_LOGIN.equals(urlApi)) {
                            objResult = jsonObject.getJSONObject(Keys.KEY_OBJECT);
                            gson = new GsonBuilder().create();
                            userResult = gson.fromJson(objResult.toString(), UserDTO.class);
                            String usename = edtUsername.getText().toString();
                            String password = edtPassword.getText().toString();
                            if (StringUtil.isNullOrEmpty(usename)) {
                                usename = SaveSharedPreference.getUserName(this);
                            }
                            if (StringUtil.isNullOrEmpty(password)) {
                                password = SaveSharedPreference.getPassword(this);
                            }
                            SaveSharedPreference.setUser(this, usename, password);
                            Constant.TOKEN = userResult.getToken();
                            String tokenNotify = FirebaseInstanceId.getInstance().getToken();

                            //--TempNoti-Start
                            Bundle bundle = new Bundle();
                            bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_HOME);
                            bundle.putSerializable(Keys.KEY_USER_LOGIN, userResult);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            //Add the bundle to the intent
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                            //---TempNoti-End

//                            saveTokenNotificationToDb(tokenNotify);

                        } else if (Constant.URL_INSERT_TOKEN_NOTIFICATION.equals(urlApi)) {
                            //package data to bundle
                            Bundle bundle = new Bundle();
                            bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_HOME);
                            bundle.putSerializable(Keys.KEY_USER_LOGIN, userResult);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            //Add the bundle to the intent
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                        btnLogin.setEnabled(true);
                        message = responseMessage;
                        typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                    } else if (Constant.RESPONSE_CODE_426 == responseStatus) {
                        btnLogin.setEnabled(true);
                        message = responseMessage;
                        typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_CHANGE_PASSWORD, true);
                    } else {
                        btnLogin.setEnabled(true);
                        message = getResources().getString(R.string.message_login_error);
                        typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                    }
                }
            } else {
                btnLogin.setEnabled(true);
                message = getResources().getString(R.string.message_error_system);
                typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
            }
        } catch (Exception e) {
            btnLogin.setEnabled(true);
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
    public void OnClickedButtonDialog(boolean isClickOk, String actionDialog, String
            typeDialog) {
        if (isClickOk) {
            switch (actionDialog) {
                case Constant.ACTION_DIALOG_DEFAULT:
                    SaveSharedPreference.setUser(this, Constant.EMPTY, Constant.EMPTY);
                    break;
                case Constant.ACTION_DIALOG_CHANGE_PASSWORD:
                    Bundle bundle = new Bundle();
                    bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_UPDATE_PASSWORD);
                    bundle.putString(Keys.KEY_USER_CODE, edtUsername.getText().toString());
                    bundle.putString(Keys.KEY_OLD_PASSWORD, edtPassword.getText().toString());
//                    bundle.putSerializable(Keys.KEY_USER_LOGIN, userResult);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //Add the bundle to the intent
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }
}
