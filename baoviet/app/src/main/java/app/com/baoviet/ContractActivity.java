package app.com.baoviet;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.datasource.DataSourceConnectionStream;
import app.com.baoviet.entity.AnnualReport;
import app.com.baoviet.entity.BillDTO;
import app.com.baoviet.entity.GeneralInfor;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.entity.UserDTO;
import app.com.baoviet.fragment.FooterFragment;
import app.com.baoviet.fragment.TabContractBenefitFragment;
import app.com.baoviet.fragment.TabContractBillFragment;
import app.com.baoviet.fragment.TabContractCostFragment;
import app.com.baoviet.fragment.TabContractCustomerInforFragment;
import app.com.baoviet.fragment.TabContractGeneralInforFragment;
import app.com.baoviet.fragment.TabContractNoticeFragment;
import app.com.baoviet.fragment.TabContractPaymentProcessFragment;
import app.com.baoviet.fragment.TabContractValueAccountFragment;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.StringUtil;

public class ContractActivity extends AppCompatActivity implements DialogEventListener, AdapterView.OnItemSelectedListener, ActivityCompat.OnRequestPermissionsResultCallback, AsyncResponse {

    private Spinner spContractNumber;
    private TextView tvContractLastestUpdate;
    private TextView tvContractActionBar;
    private TextView tvContractTypeDevice;
    private FrameLayout frameContractInfor;
    private LinearLayout lnContractSpinner;
    private String filePath;
    private String apiUrl;
    private String typeAccount;
    private String typeAction;
    private AnnualReport annualReport;
    private BillDTO billDTO;

    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;
    List<String> listAccount;
    String usercode = Constant.EMPTY;
    String accountNumber;
    String keyTransfer;
    String lastestDate;
    String productTypeCode;
    UserDTO user;
    Fragment fr = null;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
        initInterface();
        Intent intent = getIntent();
        if (intent != null) {
            //get data from bundle
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                keyTransfer = bundle.getString(Keys.KEY_TRANSFER);
                user = (UserDTO) bundle.getSerializable(Keys.KEY_USER_LOGIN);
                if (StringUtil.isNullOrEmpty(user.getAccountOtherNumber())) {
                    typeAccount = Constant.TYPE_ACCOUNT_TALISMAN;
                } else {
                    typeAccount = Constant.TYPE_ACCOUNT_BVLIFE;
                }
                getAccountList();
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
        if (Constant.TYPE_DEVICE_TABLET.equals(tvContractTypeDevice.getText())) {
            // load footer fragment
            Fragment frFooter = new FooterFragment();
            FragmentManager fmFooter = getSupportFragmentManager();
            FragmentTransaction fmTransactionFooter = fmFooter.beginTransaction();
            fmTransactionFooter.replace(R.id.lnFooterContract, frFooter);
            fmTransactionFooter.commit();
        }
    }

    public void initData() {
        try {
            if (!StringUtil.isNullOrEmpty(getAccountNumber())) {
                DataSourceConnection connection;
                connection = new DataSourceConnection(this, Constant.URL_GET_PER_GENERAL_INFOR, getAccountNumber(), Constant.METHOD_POST, false, false);
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
        spContractNumber = (Spinner) findViewById(R.id.spContractNumber);
        tvContractLastestUpdate = (TextView) findViewById(R.id.tvContractLastestUpdate);
        tvContractActionBar = (TextView) findViewById(R.id.tvContractActionBar);
        tvContractTypeDevice = (TextView) findViewById(R.id.tvContractTypeDevice);
        frameContractInfor = (FrameLayout) findViewById(R.id.frameContractInfor);
        lnContractSpinner = (LinearLayout) findViewById(R.id.lnContractSpinner);
        spContractNumber.setOnItemSelectedListener(this);

    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void getAccountList() {
        listAccount = user.getAccounts();
        if (listAccount == null) {
            message = getResources().getString(R.string.message_error_data);
            typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
        } else {
            if (Constant.INT_0 == listAccount.size()) {
                message = getResources().getString(R.string.message_error_data);
                typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
            } else {
                // spinner contract number
                showData();
            }
        }
    }

    public void showData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_layout, listAccount);
        adapter.setDropDownViewResource(R.layout.spinner_layout_popup);
        spContractNumber.setAdapter(adapter);
        if (StringUtil.isNullOrEmpty(Constant.ACCOUNT_NUMBER_SAVED)) {
            spContractNumber.setSelection(Constant.INT_0);
        } else {
            if (listAccount.contains(Constant.ACCOUNT_NUMBER_SAVED)) {
                int positionSaved = adapter.getPosition(Constant.ACCOUNT_NUMBER_SAVED);
                spContractNumber.setSelection(positionSaved);
            } else {
                spContractNumber.setSelection(Constant.INT_0);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        lnContractSpinner.setVisibility(View.VISIBLE);
        tvContractLastestUpdate.setVisibility(View.VISIBLE);
    }

    public AnnualReport getAnnualReport() {
        return annualReport;
    }

    public void setAnnualReport(AnnualReport annualReport) {
        this.annualReport = annualReport;
    }

    public BillDTO getBillDTO() {
        return billDTO;
    }

    public void setBillDTO(BillDTO billDTO) {
        this.billDTO = billDTO;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(String typeAction) {
        this.typeAction = typeAction;
    }

    public void navigationFragmentFromContract(String keyTransfer) {
        this.keyTransfer = keyTransfer;
        fm = this.getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        Intent intentContract;
        Bundle bundle;
        switch (keyTransfer) {
            case Constant.TRANSFER_LOGIN_TO_SECURITY:
                lnContractSpinner.setVisibility(View.GONE);
                tvContractLastestUpdate.setVisibility(View.GONE);
                bundle = new Bundle();
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_SECURITY);
                intentContract = new Intent(this, FooterActivity.class);
                intentContract.putExtras(bundle);
                startActivity(intentContract);
                break;
            case Constant.TRANSFER_LOGIN_TO_TERMS:
                lnContractSpinner.setVisibility(View.GONE);
                tvContractLastestUpdate.setVisibility(View.GONE);
                bundle = new Bundle();
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_TERMS);
                intentContract = new Intent(this, FooterActivity.class);
                intentContract.putExtras(bundle);
                startActivity(intentContract);
                break;
            case Constant.TRANSFER_LOGIN_TO_CONTACT:
                lnContractSpinner.setVisibility(View.GONE);
                tvContractLastestUpdate.setVisibility(View.GONE);
                bundle = new Bundle();
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_LOGIN_TO_CONTACT);
                intentContract = new Intent(this, FooterActivity.class);
                intentContract.putExtras(bundle);
                startActivity(intentContract);
                break;
            case Constant.TRANSFER_CONTRACT_TO_GENERAL_INFOR:
                lnContractSpinner.setVisibility(View.VISIBLE);
                tvContractLastestUpdate.setVisibility(View.VISIBLE);
                tvContractActionBar.setText(R.string.title_home_tab_item_general_infor);
                fr = new TabContractGeneralInforFragment();
                Bundle bundleGeneralInfor = new Bundle();
                bundleGeneralInfor.putString(Keys.KEY_ACCOUNT_NUMBER, getAccountNumber());
                bundleGeneralInfor.putString(Keys.KEY_TYPE_ACCOUNT, this.typeAccount);
                bundleGeneralInfor.putString(Keys.KEY_PRODUCT_TYPE_CODE, this.productTypeCode);
                fr.setArguments(bundleGeneralInfor);
                fragmentTransaction.replace(R.id.frameContractInfor, fr, Constant.TRANSFER_CONTRACT_TO_GENERAL_INFOR);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_CONTRACT_TO_CUSTOMER_INFOR:
                lnContractSpinner.setVisibility(View.VISIBLE);
                tvContractLastestUpdate.setVisibility(View.VISIBLE);
                tvContractActionBar.setText(R.string.title_home_tab_item_customer_infor);
                fr = new TabContractCustomerInforFragment();
                Bundle bundleCustomerInfor = new Bundle();
                bundleCustomerInfor.putString(Keys.KEY_ACCOUNT_NUMBER, getAccountNumber());
                bundleCustomerInfor.putString(Keys.KEY_TYPE_ACCOUNT, this.typeAccount);
                fr.setArguments(bundleCustomerInfor);
                fragmentTransaction.replace(R.id.frameContractInfor, fr, Constant.TRANSFER_CONTRACT_TO_CUSTOMER_INFOR);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_CONTRACT_TO_VALUE_ACCOUNT:
                lnContractSpinner.setVisibility(View.VISIBLE);
                tvContractLastestUpdate.setVisibility(View.VISIBLE);
                tvContractActionBar.setText(R.string.title_home_tab_item_value_account);
                fr = new TabContractValueAccountFragment();
                Bundle bundleValueAccount = new Bundle();
                bundleValueAccount.putString(Keys.KEY_ACCOUNT_NUMBER, getAccountNumber());
                bundleValueAccount.putString(Keys.KEY_TYPE_ACCOUNT, this.typeAccount);
                bundleValueAccount.putString(Keys.KEY_PRODUCT_TYPE_CODE, this.productTypeCode);
                fr.setArguments(bundleValueAccount);
                fragmentTransaction.replace(R.id.frameContractInfor, fr, Constant.TRANSFER_CONTRACT_TO_VALUE_ACCOUNT);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_CONTRACT_TO_PAYMENT_PROCESS:
                lnContractSpinner.setVisibility(View.VISIBLE);
                tvContractLastestUpdate.setVisibility(View.VISIBLE);
                tvContractActionBar.setText(R.string.title_home_tab_item_payment_process);
                fr = new TabContractPaymentProcessFragment();
                Bundle bundlePaymentProcess = new Bundle();
                bundlePaymentProcess.putString(Keys.KEY_ACCOUNT_NUMBER, getAccountNumber());
                bundlePaymentProcess.putString(Keys.KEY_TYPE_ACCOUNT, this.typeAccount);
                bundlePaymentProcess.putString(Keys.KEY_PRODUCT_TYPE_CODE, productTypeCode);
                fr.setArguments(bundlePaymentProcess);
                fragmentTransaction.replace(R.id.frameContractInfor, fr, Constant.TRANSFER_CONTRACT_TO_PAYMENT_PROCESS);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_CONTRACT_TO_FEE_AND_COST:
                lnContractSpinner.setVisibility(View.VISIBLE);
                tvContractLastestUpdate.setVisibility(View.VISIBLE);
                tvContractActionBar.setText(R.string.title_home_tab_item_fee_and_cost);
                fr = new TabContractCostFragment();
                Bundle bundleCost = new Bundle();
                bundleCost.putString(Keys.KEY_ACCOUNT_NUMBER, getAccountNumber());
                bundleCost.putString(Keys.KEY_TYPE_ACCOUNT, this.typeAccount);
                fr.setArguments(bundleCost);
                fragmentTransaction.replace(R.id.frameContractInfor, fr, Constant.TRANSFER_CONTRACT_TO_FEE_AND_COST);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_CONTRACT_TO_BENEFIT:
                lnContractSpinner.setVisibility(View.VISIBLE);
                tvContractLastestUpdate.setVisibility(View.VISIBLE);
                tvContractActionBar.setText(R.string.title_home_tab_item_benefit);
                fr = new TabContractBenefitFragment();
                Bundle bundleBenefit = new Bundle();
                bundleBenefit.putString(Keys.KEY_ACCOUNT_NUMBER, getAccountNumber());
                bundleBenefit.putString(Keys.KEY_TYPE_ACCOUNT, this.typeAccount);
                fr.setArguments(bundleBenefit);
                fragmentTransaction.replace(R.id.frameContractInfor, fr, Constant.TRANSFER_CONTRACT_TO_BENEFIT);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_CONTRACT_TO_EBILL:
                lnContractSpinner.setVisibility(View.VISIBLE);
                tvContractLastestUpdate.setVisibility(View.VISIBLE);
                tvContractActionBar.setText(R.string.title_home_tab_item_ebill);
                fr = new TabContractBillFragment();
                Bundle bundleEbill = new Bundle();
                bundleEbill.putString(Keys.KEY_ACCOUNT_NUMBER, getAccountNumber());
                bundleEbill.putString(Keys.KEY_TYPE_ACCOUNT, this.typeAccount);
                fr.setArguments(bundleEbill);
                fragmentTransaction.replace(R.id.frameContractInfor, fr, Constant.TRANSFER_CONTRACT_TO_EBILL);
                fragmentTransaction.commit();
                break;
            case Constant.TRANSFER_CONTRACT_TO_ANNUAL_REPORT:
                lnContractSpinner.setVisibility(View.VISIBLE);
                tvContractLastestUpdate.setVisibility(View.VISIBLE);
                tvContractActionBar.setText(R.string.title_home_tab_item_annual_report);
                fr = new TabContractNoticeFragment();
                Bundle bundleAnnualReport = new Bundle();
                bundleAnnualReport.putString(Keys.KEY_ACCOUNT_NUMBER, getAccountNumber());
                bundleAnnualReport.putString(Keys.KEY_TYPE_ACCOUNT, this.typeAccount);
                fr.setArguments(bundleAnnualReport);
                fragmentTransaction.replace(R.id.frameContractInfor, fr, Constant.TRANSFER_CONTRACT_TO_ANNUAL_REPORT);
                fragmentTransaction.commit();
                break;
        }
    }


    @Override
    public void OnClickedButtonDialog(boolean isClickOk, String actionDialog, String typeDialog) {
        if (isClickOk) {
            switch (actionDialog) {
                case Constant.ACTION_DIALOG_ACCEPT_RESTART:
                    Intent intent = new Intent(this, InitialActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    // Schedule start after 1 second
                    PendingIntent pi = PendingIntent.getActivity(
                            this,
                            0,
                            intent,
                            PendingIntent.FLAG_ONE_SHOT);
                    AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    am.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pi);
                    finish();
                    System.exit(2);
                    break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Constant.ACCOUNT_NUMBER_SAVED = listAccount.get(position);
        setAccountNumber(listAccount.get(position));
        initData();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setDataToDownloadPDF(AnnualReport annualReport, BillDTO billDTO, String typeAction) {
        setAnnualReport(annualReport);
        setTypeAction(typeAction);
        setBillDTO(billDTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File dir = null;
        File root = null;
        String fullPath = null;
        String nameFolder = Constant.EMPTY;
        root = android.os.Environment.getExternalStorageDirectory();
        DocumentFile pickedDir = null;
        if (requestCode == Constant.REQUEST_CODE_CHOOSE_FOLDER) {
            if (data != null) {
                try {
                    Uri uri = data.getData();
                    String pathTemp = uri.getPath();
                    if (pathTemp.startsWith(Constant.PATH_TREE_DOWNLOADS)) {
                        fullPath = Constant.PATH_ROOT_DOWNLOAD;
                    } else if (pathTemp.startsWith(Constant.PATH_TREE_PRIMARY)) {
                        String[] arr = pathTemp.split(Constant.SYMBOL_COLON);
                        if (Constant.INT_1 == arr.length) {
                            fullPath = Constant.PATH_ROOT;
                        } else if (Constant.INT_1 < arr.length) {
                            nameFolder = arr[1];
                            fullPath = root.getAbsolutePath() + Constant.SYMBOL_SLASH + nameFolder + Constant.SYMBOL_SLASH;
                        }
                    } else if (pathTemp.startsWith(Constant.PATH_TREE_RAW)) {
                        nameFolder = pathTemp.split(Constant.SYMBOL_COLON)[1];
                        fullPath = nameFolder + Constant.SYMBOL_SLASH;
                    } else {
                        pickedDir = DocumentFile.fromTreeUri(this, uri);
                        grantUriPermission(getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        fullPath = Constant.EMPTY;
                    }
                    if (fullPath != null) {
                        dir = new File(fullPath);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        callAPI(fullPath, getAnnualReport(), getBillDTO(), getTypeAction(), pickedDir);
                    } else {
                        message = this.getResources().getString(R.string.message_infor_no_folder);
                        typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, false);
                    }
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                    message = this.getResources().getString(R.string.message_error_system);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                }
            }
        }
    }

    public void callAPI(String pathAbsolute, AnnualReport annualReport, BillDTO billDTO, String typeAction, DocumentFile pickedDir) {
        String pathFile = Constant.EMPTY;
        String linkApi = Constant.EMPTY;
        try {
            DataSourceConnectionStream connection;
            JSONObject paramsJsonObject = new JSONObject();

            if (getAnnualReport() != null) {
                String fileName = StringUtil.getNameFilePDF(annualReport.getContractFilePath());
                pathFile = pathAbsolute + fileName;
                linkApi = Constant.URL_POST_DOWNLOAD_REPORT_ANNUAL;
                //set params
                paramsJsonObject.put(Keys.KEY_CONTRACT_AGENT_CODE, annualReport.getContractAgentCode());
                paramsJsonObject.put(Keys.KEY_CONTRACT_AGENT_NAME, annualReport.getContractAgentName());
                paramsJsonObject.put(Keys.KEY_CONTRACT_BUYER, annualReport.getContractBuyer());
                paramsJsonObject.put(Keys.KEY_CONTRACT_CONPANY, annualReport.getContractCompany());
                paramsJsonObject.put(Keys.KEY_CREATE_DATE, StringUtil.formatDateDDMMYYFromDate(annualReport.getContractCreateDate(), Constant.YYYYMMDD));
                paramsJsonObject.put(Keys.KEY_DEADLINE, StringUtil.formatDateDDMMYYFromDate(annualReport.getContractDeadline(), Constant.YYYYMMDD));
                paramsJsonObject.put(Keys.KEY_FILE_PATH, annualReport.getContractFilePath());
                paramsJsonObject.put(Keys.KEY_CONTRACT_ID, annualReport.getContractId());
                paramsJsonObject.put(Keys.KEY_CONTRACT_POLICY_HOLDER, annualReport.getContractPolicyholder());
                paramsJsonObject.put(Keys.KEY_UPDATE_DATE, annualReport.getContractUpdateDate());
                paramsJsonObject.put(Keys.KEY_CONTRACT_YEAR, annualReport.getContractYear());
            } else if (getBillDTO() != null) {
                if (Constant.PDF_TYPE_DOWNLOAD.equals(typeAction)) {
                    pathFile = pathAbsolute + "HD" + billDTO.getBillAccountNumber() + ".pdf";
                    linkApi = Constant.URL_POST_DOWNLOAD_EBILL;
                } else if (Constant.XML_TYPE_DOWNLOAD.equals(typeAction)) {
                    pathFile = pathAbsolute + "HD" + billDTO.getBillAccountNumber() + ".xml";
                    linkApi = Constant.URL_POST_DOWNLOAD_XML_EBILL;
                }
                paramsJsonObject.put(Keys.KEY_BILL_ACCOUNT_NUMBER, billDTO.getBillAccountNumber());
                paramsJsonObject.put(Keys.KEY_BILL_ADDRESS, billDTO.getBillAddress());
                paramsJsonObject.put(Keys.KEY_BILL_BILL_NUMBER, billDTO.getBillBillNumber());
                paramsJsonObject.put(Keys.KEY_BILL_CREATEDDATE, billDTO.getBillCreatedDate());
                paramsJsonObject.put(Keys.KEY_BILL_DOWNLOAD_QUANTITY, billDTO.getBillDownloadQuantity());
                paramsJsonObject.put(Keys.KEY_BILL_EFFECT_DATE, StringUtil.formatDateDDMMYYFromDate(billDTO.getBillEffectDate(), Constant.YYYYMMDD));
                paramsJsonObject.put(Keys.KEY_BILL_FROM_CHARGEPERIOD, StringUtil.formatDateDDMMYYFromDate(billDTO.getBillFromChargePeriod(), Constant.YYYYMMDD));
                paramsJsonObject.put(Keys.KEY_BILL_INVOICE_AMOUNT, billDTO.getBillInvoiceAmount());
                paramsJsonObject.put(Keys.KEY_BILL_OUTLET_COMPANY_NAME, billDTO.getBillOutletCmpnyName());
                paramsJsonObject.put(Keys.KEY_BILL_OUTLET_NAME, billDTO.getBillOutletName());
                paramsJsonObject.put(Keys.KEY_BILL_OUTLET_NUMBER, billDTO.getBillOutletNumber());
                paramsJsonObject.put(Keys.KEY_BILL_PHONE_NUMBER, billDTO.getBillPhoneNumber());
                paramsJsonObject.put(Keys.KEY_BILL_POLICY_HOLDER, billDTO.getBillPolicyholder());
                paramsJsonObject.put(Keys.KEY_BILL_STATUS, billDTO.getBillStatus());
                paramsJsonObject.put(Keys.KEY_BILL_TAX, billDTO.getBillTax());
                paramsJsonObject.put(Keys.KEY_BILL_TO_CHARGE_PERIOD, StringUtil.formatDateDDMMYYFromDate(billDTO.getBillToChargePeriod(), Constant.YYYYMMDD));
                paramsJsonObject.put(Keys.KEY_BILL_UPDATE_DATE, billDTO.getBillUpdatedDate());
                paramsJsonObject.put(Keys.KEY_BILL_VIEW_QUANTITY, billDTO.getBillViewQuantity());
                paramsJsonObject.put(Keys.KEY_BILL_XML_PATH, billDTO.getBillXmlPath());
                paramsJsonObject.put(Keys.KEY_BILL_OPTION_HANDLE, "downloadPdf");
            }

            connection = new DataSourceConnectionStream(this, linkApi, paramsJsonObject, Constant.METHOD_POST, true, pathFile, typeAction, pickedDir);
            connection.delegate = this;
            connection.execute();

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            message = this.getResources().getString(R.string.message_error_system);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    message = getResources().getString(R.string.message_granted);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_ACCEPT_RESTART, false);
                } else {
                    message = getResources().getString(R.string.message_denied);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, false);
                }
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
////                    callAPI(getApiUrl(), getAnnualReport(), Constant.PDF_TYPE_DOWNLOAD);
//                    message = getResources().getString(R.string.message_granted);
//                    typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
//                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, false);
//                } else {
//                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }


    @Override
    public void processFinish(String result, String api) {
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
                if (Constant.RESPONSE_CODE_200 == responseStatus) {
                    JSONObject objResult = jsonObject.getJSONObject(Keys.KEY_OBJECT);
                    Gson gson = new GsonBuilder().create();
                    GeneralInfor generalInfor = gson.fromJson(objResult.toString(), GeneralInfor.class);
                    this.lastestDate = generalInfor.getLatestDate();
                    this.productTypeCode = generalInfor.getProductTypeCode();
                    if (generalInfor.isBvlife()) {
                        this.typeAccount = Constant.TYPE_ACCOUNT_BVLIFE;
                    } else {
                        this.typeAccount = Constant.TYPE_ACCOUNT_TALISMAN;
                    }
                    lnContractSpinner.setVisibility(View.VISIBLE);
                    tvContractLastestUpdate.setVisibility(View.VISIBLE);
                    SpannableString content = new SpannableString(StringUtil.setTextValue(this.lastestDate));
                    content.setSpan(new StyleSpan(Typeface.ITALIC), 0, StringUtil.setTextValue(this.lastestDate).length(), 0);
                    tvContractLastestUpdate.setText(content);
                    navigationFragmentFromContract(this.keyTransfer);

                } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                    message = responseMessage;
                    typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                } else {
                    message = getResources().getString(R.string.message_error_system);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
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
        if (inputStream == null) {
            message = this.getResources().getString(R.string.message_error_system);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
        } else {
            String textResult = inputStream.getResponseText();
            if (Constant.STREAM_RESPONSE_TEXT_NETWORK_ERROR.equals(textResult)) {
                message = this.getResources().getString(R.string.message_error_network);
                typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
            } else if (Constant.STREAM_RESPONSE_TEXT_SUCCESS.equals(textResult)) {
                message = this.getResources().getString(R.string.message_download_success);
                typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, false);
            } else if (Constant.STREAM_RESPONSE_TEXT_FILE_NOT_FOUND.equals(textResult)) {
                message = this.getResources().getString(R.string.message_error_file_not_found);
                typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, false);
            } else if (Constant.STREAM_RESPONSE_TEXT_UNSUCCESS_404.equals(textResult)) {
                message = this.getResources().getString(R.string.message_download_unsuccess);
                typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, false);
            } else {
                message = this.getResources().getString(R.string.message_error_system);
                typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
            }
        }
    }
}
