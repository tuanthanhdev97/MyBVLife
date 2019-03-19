package app.com.baoviet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;

import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.entity.AnnualReport;
import app.com.baoviet.entity.BillDTO;
import app.com.baoviet.fragment.FooterFragment;
import app.com.baoviet.fragment.ViewPDFFragment;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;

public class ViewPDFActivity extends AppCompatActivity implements DialogEventListener {

    private TextView tvViewPDFActionBar;
    private TextView tvViewPDFTypeDevice;
    private FrameLayout frameViewPDF;
    private AnnualReport annualReport;
    private BillDTO billDTO;

    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    String keyTransfer;
    Fragment fr = null;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        initInterface();
        Intent intent = getIntent();
        if (intent != null) {
            //get data from bundle
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                keyTransfer = bundle.getString(Keys.KEY_TRANSFER);
                annualReport = (AnnualReport) bundle.getSerializable(Keys.KEY_ANNUAL_REPORT);
                billDTO = (BillDTO) bundle.getSerializable(Keys.KEY_BILL_DTO);
                navigationFragmentFromFooter(keyTransfer);
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
        if (Constant.TYPE_DEVICE_TABLET.equals(tvViewPDFTypeDevice.getText())) {
            // load footer fragment
            Fragment frFooter = new FooterFragment();
            FragmentManager fmFooter = getSupportFragmentManager();
            FragmentTransaction fmTransactionFooter = fmFooter.beginTransaction();
            fmTransactionFooter.replace(R.id.lnFooterViewPDF, frFooter);
            fmTransactionFooter.commit();
        }
    }


    public void navigationFragmentFromFooter(String keyTransfer) {
        this.keyTransfer = keyTransfer;
        fm = this.getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        Intent intentViewPDF;
        Bundle bundle;
        switch (keyTransfer) {
            case Constant.TRANSFER_LOGIN_TO_SECURITY:
                bundle = new Bundle();
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_SECURITY);
                intentViewPDF = new Intent(this, FooterActivity.class);
                intentViewPDF.putExtras(bundle);
                startActivity(intentViewPDF);
                break;
            case Constant.TRANSFER_LOGIN_TO_TERMS:
                bundle = new Bundle();
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_TERMS);
                intentViewPDF = new Intent(this, FooterActivity.class);
                intentViewPDF.putExtras(bundle);
                startActivity(intentViewPDF);
                break;
            case Constant.TRANSFER_LOGIN_TO_CONTACT:
                bundle = new Bundle();
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_CONTACT);
                intentViewPDF = new Intent(this, FooterActivity.class);
                intentViewPDF.putExtras(bundle);
                startActivity(intentViewPDF);
                break;
            case Constant.TRANSFER_TO_VIEW_PDF:
                fr = new ViewPDFFragment();
                Bundle bundleViewPDF = new Bundle();
                bundleViewPDF.putSerializable(Keys.KEY_ANNUAL_REPORT, annualReport);
                bundleViewPDF.putSerializable(Keys.KEY_BILL_DTO, billDTO);
                fr.setArguments(bundleViewPDF);
                fragmentTransaction.replace(R.id.frameViewPDF, fr, Constant.TRANSFER_TO_VIEW_PDF);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void OnClickedButtonDialog(boolean isClickOk, String actionDialog, String typeDialog) {

    }

    public void initInterface() {
        alert = new AlertDialogCustom(this);
        alert.dialogEventListener = this;
        tvViewPDFActionBar = (TextView) findViewById(R.id.tvViewPDFActionBar);
        tvViewPDFTypeDevice = (TextView) findViewById(R.id.tvViewPDFTypeDevice);
        frameViewPDF = (FrameLayout) findViewById(R.id.frameViewPDF);
        tvViewPDFActionBar.setText(R.string.title_home_tab_item_annual_report_view_pdf);
    }

}
