package app.com.baoviet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import app.com.baoviet.adapter.NotificationAdpater;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.entity.NotificationDTO;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.entity.UserDTO;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.StringUtil;

public class NotificationActivity extends AppCompatActivity implements AsyncResponse, DialogEventListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ListView lvNotification;
    private TextView tvNotificationActionBar;
    private String userCode;
    private UserDTO userDTO;
    private List<NotificationDTO> listNotification;
    private SwipeRefreshLayout swipeNotification;
    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initInterface();
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                userDTO = (UserDTO) bundle.getSerializable(Keys.KEY_USER_LOGIN);
                userCode = userDTO.getUserCode();
            } else {
                message = getResources().getString(R.string.message_error_system);
                typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, false);
            }
        } else {
            message = getResources().getString(R.string.message_error_system);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, false);
        }
        getInitData();
    }

    public void addDrawerItems() {
        NotificationAdpater notificationAdpater = new NotificationAdpater(this, R.layout.list_notification_item, listNotification);
        lvNotification.setAdapter(notificationAdpater);
        lvNotification.setOnItemClickListener(this);
        swipeNotification.setRefreshing(false);
    }

    public void getInitData() {
        try {
            if (!StringUtil.isNullOrEmpty(userCode)) {
                DataSourceConnection connection;
                connection = new DataSourceConnection(this, Constant.URL_GET_NOTIFICATION, Constant.NUMBER_RECORD_NOTIFICATION, Constant.METHOD_POST, false, false);
                connection.delegate = this;
                connection.execute();

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


    public void initInterface() {
        alert = new AlertDialogCustom(this);
        alert.dialogEventListener = this;
        lvNotification = (ListView) findViewById(R.id.lvNotification);
        tvNotificationActionBar = (TextView) findViewById(R.id.tvNotificationActionBar);
        swipeNotification = (SwipeRefreshLayout) findViewById(R.id.swipeNotification);
        swipeNotification.setOnRefreshListener(this);
        tvNotificationActionBar.setText(R.string.txt_notification_header);
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
                        JSONArray arrResult = jsonObject.getJSONArray(Keys.KEY_OBJECT);
                        Gson gson = new GsonBuilder().create();
                        Type listType = new TypeToken<List<NotificationDTO>>() {
                        }.getType();
                        List<NotificationDTO> myModelList = gson.fromJson(arrResult.toString(), listType);
                        if (myModelList == null || Constant.INT_0 == myModelList.size()) {
                            swipeNotification.setRefreshing(false);
                            message = getResources().getString(R.string.message_value_account_no_data);
                            typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                        } else {
                            listNotification = myModelList;
                            addDrawerItems();
                        }
                    } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                        message = responseMessage;
                        typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                    } else {
                        message = getResources().getString(R.string.message_value_account_no_data);
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
            switch (actionDialog) {
                case Constant.ACTION_DIALOG_DEFAULT:
                    onBackPressed();
                    break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NotificationDTO notify = listNotification.get(position);
        String notifyType = notify.getNotifyType();
        String notifyDes = notify.getNotifyTypeDescription();
        switch (notifyType) {
            case Constant.NOTIFY_TYPE_FUNCTION:
                switch (notifyDes) {
                    case Constant.NOTIFY_TYPE_DES_PAYMENT:
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Keys.KEY_USER_LOGIN, userDTO);
                        bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_PAYMENT);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        //Add the bundle to the intent
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                }
                break;
            case Constant.NOTIFY_TYPE_LINK:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(notifyDes));
                startActivity(intent);
                break;
            case Constant.NOTIFY_TYPE_OTHER:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Keys.KEY_USER_LOGIN, userDTO);
        bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_HOME);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //Add the bundle to the intent
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRefresh() {
        getInitData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
