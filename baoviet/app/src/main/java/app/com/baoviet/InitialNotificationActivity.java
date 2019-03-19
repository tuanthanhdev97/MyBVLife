package app.com.baoviet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.StringUtil;

public class InitialNotificationActivity extends AppCompatActivity implements AsyncResponse, DialogEventListener {

    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    private JSONObject objResult;
    private Gson gson;
    private UserDTO userResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_notification);
        initData();
        checkAutoLogin();
    }

    public void initData() {
        alert = new AlertDialogCustom(this);
        alert.dialogEventListener = this;
    }

    public void checkAutoLogin() {
        String username = SaveSharedPreference.getUserName(this);
        if (!StringUtil.isNullOrEmpty(username)) {
            try {
                DataSourceConnection connection;
                JSONObject postDataParams = new JSONObject();
                postDataParams.put(Keys.KEY_USERNAME, SaveSharedPreference.getUserName(this));
                postDataParams.put(Keys.KEY_PASSWORD, SaveSharedPreference.getPassword(this));
                String inputParams = postDataParams.toString().replace("\\", "").replace("\"[", "[").replace("]\"", "]");

                connection = new DataSourceConnection(this, Constant.URL_LOGIN, inputParams, Constant.METHOD_POST);
                connection.delegate = this;
                connection.execute();

            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
                message = getResources().getString(R.string.message_error_system);
                typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
            }
        } else {
            Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
            intentLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentLogin);
            finish();
        }
    }

    private void saveTokenNotificationToDb(String tokenNof) {
        try {
            DataSourceConnection connection;
            connection = new DataSourceConnection(this, Constant.URL_INSERT_TOKEN_NOTIFICATION, tokenNof, Constant.METHOD_POST);
            connection.delegate = this;
            connection.execute();

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
                if (Constant.ERROR_SERVER.equals(result) || Constant.ERROR_NETWORK.equals(result)) {
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
//                        String usename = edtUsername.getText().toString();
//                        String password = edtPassword.getText().toString();
//                        if (StringUtil.isNullOrEmpty(usename)) {
//                            usename = SaveSharedPreference.getUserName(this);
//                        }
//                        if (StringUtil.isNullOrEmpty(password)) {
//                            password = SaveSharedPreference.getPassword(this);
//                        }
//                        SaveSharedPreference.setUser(this, usename, password);


                            Constant.TOKEN = userResult.getToken();
                            String tokenNotify = FirebaseInstanceId.getInstance().getToken();

                            //TempNoti-start
                            Bundle bundle = new Bundle();
                            bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_TO_NOTIFICATION_PAGE);
                            bundle.putSerializable(Keys.KEY_USER_LOGIN, userResult);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            //Add the bundle to the intent
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                            //TempNoti-end


//                            saveTokenNotificationToDb(tokenNotify);

                        } else if (Constant.URL_INSERT_TOKEN_NOTIFICATION.equals(urlApi)) {
                            //package data to bundle
                            Bundle bundle = new Bundle();
                            bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_TO_NOTIFICATION_PAGE);
                            bundle.putSerializable(Keys.KEY_USER_LOGIN, userResult);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            //Add the bundle to the intent
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                        message = responseMessage;
                        typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                    } else {
                        message = getResources().getString(R.string.message_login_error);
                        typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
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
                case Constant.ACTION_DIALOG_DEFAULT:
                    SaveSharedPreference.setUser(this, Constant.EMPTY, Constant.EMPTY);
                    Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    intentLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intentLogin);
                    finish();
                    break;
            }
        }
    }
}
