package app.com.baoviet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.fragment.ContactFragment;
import app.com.baoviet.fragment.FooterFragment;
import app.com.baoviet.fragment.SecurityFragment;
import app.com.baoviet.fragment.TermFragment;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;

public class FooterActivity extends AppCompatActivity implements DialogEventListener {

    private TextView tvFooterActionBar;
    private TextView tvFooterTypeDevice;
    private FrameLayout frameFooter;

    String keyTransfer;

    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    Fragment fr = null;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footer);
        initInterface();

        Intent intent = getIntent();
        if (intent != null) {
            //get data from bundle
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                keyTransfer = bundle.getString(Keys.KEY_TRANSFER);
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
        if (Constant.TYPE_DEVICE_TABLET.equals(tvFooterTypeDevice.getText())) {
            // load footer fragment
            Fragment frFooter = new FooterFragment();
            FragmentManager fmFooter = getSupportFragmentManager();
            FragmentTransaction fmTransactionFooter = fmFooter.beginTransaction();
            fmTransactionFooter.replace(R.id.lnFooterContract, frFooter);
            fmTransactionFooter.commit();
        }
        navigationFragmentFromFooter(keyTransfer);
    }

    public void initInterface() {
        tvFooterActionBar = (TextView) findViewById(R.id.tvFooterActionBar);
        tvFooterTypeDevice = (TextView) findViewById(R.id.tvFooterTypeDevice);
        frameFooter = (FrameLayout) findViewById(R.id.frameFooter);

    }

    public void navigationFragmentFromFooter(String keyTransfer) {
        fm = this.getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        switch (keyTransfer) {
            case Constant.TRANSFER_LOGIN_TO_SECURITY:
                fr = new SecurityFragment();
                tvFooterActionBar.setVisibility(View.VISIBLE);
                tvFooterActionBar.setText(R.string.txt_Login_Security);
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameFooter, fr, Constant.TRANSFER_LOGIN_TO_SECURITY);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_LOGIN_TO_TERMS:
                fr = new TermFragment();
                tvFooterActionBar.setVisibility(View.VISIBLE);
                tvFooterActionBar.setText(R.string.txt_Login_Terms);
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameFooter, fr, Constant.TRANSFER_LOGIN_TO_TERMS);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_LOGIN_TO_CONTACT:
                fr = new ContactFragment();
                tvFooterActionBar.setVisibility(View.VISIBLE);
                tvFooterActionBar.setText(R.string.txt_Login_Contact);
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameFooter, fr, Constant.TRANSFER_LOGIN_TO_CONTACT);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void OnClickedButtonDialog(boolean isClickOk, String actionDialog, String typeDialog) {

    }
}
