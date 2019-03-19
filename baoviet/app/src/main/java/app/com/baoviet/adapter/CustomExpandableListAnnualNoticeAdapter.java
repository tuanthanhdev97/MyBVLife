package app.com.baoviet.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import app.com.baoviet.ContractActivity;
import app.com.baoviet.R;
import app.com.baoviet.ViewPDFActivity;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnectionStream;
import app.com.baoviet.entity.AnnualReport;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.fragment.TabContractNoticeFragment;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.GrantPermission;
import app.com.baoviet.utility.StringUtil;

public class CustomExpandableListAnnualNoticeAdapter extends BaseExpandableListAdapter implements AsyncResponse, DialogEventListener {

    private Context mContext;
    private List<AnnualReport> mExpandableListPreminum;
    private LayoutInflater mLayoutInflater;
    private AnnualReport annualReport;
    private TabContractNoticeFragment fragment;

    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public CustomExpandableListAnnualNoticeAdapter(Context context, List<AnnualReport> mExpandableListPreminum, TabContractNoticeFragment fragment) {
        this.mExpandableListPreminum = mExpandableListPreminum;
        this.mContext = context;
        this.fragment = fragment;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        alert = new AlertDialogCustom(mContext);
        alert.dialogEventListener = this;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        AnnualReport transPreminum = null;
        if (mExpandableListPreminum != null) {
            transPreminum = mExpandableListPreminum.get(listPosition);
            return transPreminum;
        }
        return null;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final AnnualReport expandedListText = (AnnualReport) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_annual_notice_tab_item, null);
        }
        TextView tvContractTabNoticeEffectiveDate = (TextView) convertView.findViewById(R.id.tvContractTabNoticeEffectiveDate);
        TextView tvContractTabNoticeReceiver = (TextView) convertView.findViewById(R.id.tvContractTabNoticeReceiver);
        TextView tvContractTabNoticeCodeTVV = (TextView) convertView.findViewById(R.id.tvContractTabNoticeCodeTVV);
        TextView tvContractTabNoticeNameTVV = (TextView) convertView.findViewById(R.id.tvContractTabNoticeNameTVV);
        TextView tvContractTabNoticeCompany = (TextView) convertView.findViewById(R.id.tvContractTabNoticeCompany);
        TextView tvContactTabNoticeItemTypeDevice = (TextView) convertView.findViewById(R.id.tvContactTabNoticeItemTypeDevice);

        String typeDevice = tvContactTabNoticeItemTypeDevice.getText().toString();
        tvContractTabNoticeReceiver.setText(StringUtil.setTextValue(expandedListText.getContractPolicyholder()));
        tvContractTabNoticeEffectiveDate.setText(StringUtil.formatDateDDMMYYFromDate(expandedListText.getContractDeadline(), Constant.DDMMYY));
        tvContractTabNoticeCodeTVV.setText(StringUtil.setTextValue(expandedListText.getContractAgentCode()));
        tvContractTabNoticeNameTVV.setText(StringUtil.setTextValue(expandedListText.getContractAgentName()));
        tvContractTabNoticeCompany.setText(StringUtil.setTextValue(expandedListText.getContractCompany()));
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        if (mExpandableListPreminum != null) {
            return Constant.INT_1;
        }
        return Constant.INT_0;
    }

    @Override
    public Object getGroup(int listPosition) {
        return mExpandableListPreminum.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return mExpandableListPreminum.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        annualReport = (AnnualReport) getGroup(listPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_annual_notice_tab_group, null);
        }
        TextView tvNoticeTabHeaderNumberContract = (TextView) convertView.findViewById(R.id.tvNoticeTabHeaderNumberContract);
        TextView tvNoticeTabHeaderYear = (TextView) convertView.findViewById(R.id.tvNoticeTabHeaderYear);
        TextView tvNoticeTabHeaderBuyer = (TextView) convertView.findViewById(R.id.tvNoticeTabHeaderBuyer);
        TextView tvContactTabNoticeTypeDevice = (TextView) convertView.findViewById(R.id.tvContactTabNoticeTypeDevice);
        LinearLayout lnNoticeTabHeaderShow = (LinearLayout) convertView.findViewById(R.id.lnNoticeTabHeaderShow);
        LinearLayout lnNoticeTabHeaderDownload = (LinearLayout) convertView.findViewById(R.id.lnNoticeTabHeaderDownload);
        ImageView imgArrowNotice = (ImageView) convertView.findViewById(R.id.imgArrowNotice);
        String typeDevice = tvContactTabNoticeTypeDevice.getText().toString();
        tvNoticeTabHeaderNumberContract.setText(StringUtil.setTextValue(annualReport.getContractId()));
        tvNoticeTabHeaderYear.setText(StringUtil.setTextValue(annualReport.getContractYear().toString()));
        tvNoticeTabHeaderBuyer.setText(StringUtil.setTextValue(annualReport.getContractBuyer()));
        lnNoticeTabHeaderShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ViewPDFActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Keys.KEY_TRANSFER, Constant.TRANSFER_TO_VIEW_PDF);
                bundle.putSerializable(Keys.KEY_ANNUAL_REPORT, annualReport);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        lnNoticeTabHeaderDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GrantPermission.hasPermissionWriteExternalStorage(mContext)) {
                    ((ContractActivity) mContext).setDataToDownloadPDF(annualReport, null, Constant.PDF_TYPE_DOWNLOAD);
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                    ((Activity) mContext).startActivityForResult(intent, Constant.REQUEST_CODE_CHOOSE_FOLDER);
                }
            }
        });

        int countChild = getChildrenCount(listPosition);
        if (Constant.INT_0 < countChild) {
            imgArrowNotice.setVisibility(View.VISIBLE);
            if (isExpanded) {
                imgArrowNotice.setImageResource(R.drawable.ic_arrow_up);
            } else {
                imgArrowNotice.setImageResource(R.drawable.ic_arrow_down);
            }
        } else {
            imgArrowNotice.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void callAPI(String linkApi, AnnualReport annualReport, String typeAction) {
        try {
//            Intent chooseFile;
//            Intent intent;
//            chooseFile = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
//            chooseFile.setType("file/*");
//            intent = Intent.createChooser(chooseFile, "Choose a file");
//            ((Activity)mContext).startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);

            File dir = null;
            File root = null;
            File file = null;
            DataSourceConnectionStream connection;
            JSONObject paramsJsonObject = new JSONObject();
            String fileName = StringUtil.getNameFilePDF(annualReport.getContractFilePath());
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

            if (Constant.PDF_TYPE_DOWNLOAD.equals(typeAction)) {
                root = android.os.Environment.getExternalStorageDirectory();
                dir = new File(root.getAbsolutePath() + Constant.PATH_OF_FOLDER_SAVE_PDF);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
            }
            fileName = dir.getAbsolutePath() + Constant.SYMBOL_SLASH + fileName;
            connection = new DataSourceConnectionStream((Activity) mContext, linkApi, paramsJsonObject, Constant.METHOD_POST, true, fileName, typeAction);
            connection.delegate = this;
            connection.execute();

        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            message = mContext.getResources().getString(R.string.message_error_system);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
        }
    }

    @Override
    public void processFinish(String result, String urlApi) {

    }

    @Override
    public void processFinishStream(ResponseStream inputStream) {
        if (inputStream == null) {
            message = mContext.getResources().getString(R.string.message_error_system);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
        } else {
            String textResult = inputStream.getResponseText();
            if (Constant.STREAM_RESPONSE_TEXT_NETWORK_ERROR.equals(textResult)) {
                message = mContext.getResources().getString(R.string.message_error_network);
                typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
            } else if (Constant.STREAM_RESPONSE_TEXT_SUCCESS.equals(textResult)) {
                message = mContext.getResources().getString(R.string.message_download_success);
                typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, false);
            } else if (Constant.STREAM_RESPONSE_TEXT_FILE_NOT_FOUND.equals(textResult)) {
                message = mContext.getResources().getString(R.string.message_error_file_not_found);
                typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, false);
            } else {
                message = mContext.getResources().getString(R.string.message_error_system);
                typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
            }
        }
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    @Override
    public void OnClickedButtonDialog(boolean isClickOk, String actionDialog, String typeDialog) {
    }

    public boolean verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            return false;
        } else {
            return true;
        }
    }
}
