package app.com.baoviet.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.com.baoviet.InitialActivity;
import app.com.baoviet.MainActivity;
import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.datasource.SaveSharedPreference;
import app.com.baoviet.interfaceclass.DialogEventListener;

/**
 * Created by Administrator on 5/17/2018.
 */

public class AlertDialogCustom extends Dialog implements View.OnClickListener {

    private Context context;
    private TextView tvDialogTitle;
    private TextView tvDialogMessage;
    private LinearLayout lnDialogBtnOk;
    private LinearLayout lnDialogBtnCancel;
    private Button btnDialogOk;
    private Button btnDialogCancel;
    public DialogEventListener dialogEventListener;

    private String typeDialog = Constant.EMPTY;
    private String actionDialog;
    private boolean isTranserToMainActivity;

    public AlertDialogCustom(Context context) {
        super(context);
        this.context = context;
        this.setContentView(R.layout.alert_dialog_custom);
        initInterface();
    }

    public void initInterface() {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tvDialogTitle = (TextView) findViewById(R.id.tvDialogTitle);
        tvDialogMessage = (TextView) findViewById(R.id.tvDialogMessage);
        lnDialogBtnOk = (LinearLayout) findViewById(R.id.lnDialogBtnOk);
        lnDialogBtnCancel = (LinearLayout) findViewById(R.id.lnDialogBtnCancel);
        btnDialogOk = (Button) findViewById(R.id.btnDialogOk);
        btnDialogCancel = (Button) findViewById(R.id.btnDialogCancel);
        btnDialogCancel.setOnClickListener(this);
        btnDialogOk.setOnClickListener(this);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
//        super.dismiss();
    }

    public void showDialog(String message, String typeDialog, String actionDialog, boolean isTranserToMainActivity) {
        this.isTranserToMainActivity = isTranserToMainActivity;
        this.typeDialog = typeDialog;
        this.actionDialog = actionDialog;
        String titleDialog = Constant.EMPTY;
        switch (typeDialog) {
            case Constant.TYPE_ALERT_DIALOG_INFOR:
                titleDialog = Constant.TYPE_TITLE_ALERT_DIALOG_INFOR;
                lnDialogBtnCancel.setVisibility(View.GONE);
                tvDialogMessage.setText(StringUtil.setTextValue(message));
                break;
            case Constant.TYPE_ALERT_DIALOG_WARNING:
                titleDialog = Constant.TYPE_TITLE_ALERT_DIALOG_WARNING;
                lnDialogBtnCancel.setVisibility(View.GONE);
                tvDialogMessage.setText(StringUtil.setTextValue(message));
                break;
            case Constant.TYPE_ALERT_DIALOG_ERROR:
                titleDialog = Constant.TYPE_TITLE_ALERT_DIALOG_ERROR;
                lnDialogBtnCancel.setVisibility(View.GONE);
                tvDialogMessage.setText(StringUtil.setTextValue(message));
                break;
            case Constant.TYPE_ALERT_DIALOG_EXCEPTION:
                titleDialog = Constant.TYPE_TITLE_ALERT_DIALOG_ERROR;
                lnDialogBtnCancel.setVisibility(View.GONE);
                tvDialogMessage.setText(StringUtil.setTextValue(message));
                break;
            case Constant.TYPE_ALERT_DIALOG_CONFIRM:
                titleDialog = Constant.TYPE_TITLE_ALERT_DIALOG_CONFIRM;
                lnDialogBtnCancel.setVisibility(View.VISIBLE);
                tvDialogMessage.setText(StringUtil.setTextValue(message));
                break;
            case Constant.TYPE_ALERT_DIALOG_CONFIRM_INFOR:
                titleDialog = Constant.TYPE_TITLE_ALERT_DIALOG_CONFIRM_INFOR;
                lnDialogBtnCancel.setVisibility(View.VISIBLE);
//                tvDialogMessage.setText(StringUtil.setTextValue(message));
                tvDialogMessage.setText(Html.fromHtml(message));
                break;
            case Constant.TYPE_ALERT_DIALOG_LINK:
                titleDialog = Constant.TYPE_TITLE_ALERT_DIALOG_LINK;
                lnDialogBtnCancel.setVisibility(View.GONE);

                String[] arrResponse = message.split("<a");
                String strContent = arrResponse[0];
                String strLink = arrResponse[1];
                String strLinkContent = strLink.substring(strLink.indexOf("<u>"), strLink.indexOf("</u>")).replace("<u>", "");
                String strLinkRemain = strLink.substring(strLink.indexOf("</a>")).replace("</a>", "");

                SpannableString link = StringUtil.makeLinkSpan(strLinkContent, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity)context).navigationFragment(Constant.TRANSFER_LOGIN_TO_FORGOT_PASSWORD);
                        AlertDialogCustom.super.dismiss();
                    }
                });

                // Set the TextView's text
                tvDialogMessage.setText(Html.fromHtml(strContent));

                // Append the link we created above using a function defined below.
                tvDialogMessage.append(link);

                // Append a period (this will not be a link).
                tvDialogMessage.append(Html.fromHtml(strLinkRemain));

                // This line makes the link clickable!
                StringUtil.makeLinksFocusable(tvDialogMessage);

                break;

        }
        tvDialogTitle.setText(StringUtil.setTextValue(titleDialog));
        show();
    }

    @Override
    public void onClick(View view) {
        try {
            boolean isClickOk = false;
            switch (view.getId()) {
                case R.id.btnDialogOk:
                    super.dismiss();
                    isClickOk = true;
                    switch (typeDialog) {

                        case Constant.TYPE_ALERT_DIALOG_EXCEPTION:
                            if (isTranserToMainActivity) {
                                SaveSharedPreference.setUser(context, Constant.EMPTY, Constant.EMPTY);
                                Constant.ACCOUNT_NUMBER_SAVED = Constant.EMPTY;
                                Intent intent = new Intent(context, InitialActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            }
                            break;
                        case Constant.TYPE_ALERT_DIALOG_ERROR:

                        default:
                            dialogEventListener.OnClickedButtonDialog(isClickOk, actionDialog, typeDialog);
                            break;
                    }
                    break;
                case R.id.btnDialogCancel:
                    isClickOk = false;
                    super.dismiss();
                    break;
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
    }
}
