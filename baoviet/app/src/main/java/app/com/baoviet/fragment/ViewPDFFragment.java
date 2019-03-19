package app.com.baoviet.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;

import org.json.JSONObject;

import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnectionStream;
import app.com.baoviet.entity.AnnualReport;
import app.com.baoviet.entity.BillDTO;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.StringUtil;

public class ViewPDFFragment extends Fragment implements AsyncResponse, DialogEventListener {
    private PDFView pdfView;
    private AnnualReport annualReport;
    private BillDTO billDTO;
    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpdf, container, false);
        initInterface(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            annualReport = (AnnualReport) bundle.getSerializable(Keys.KEY_ANNUAL_REPORT);
            billDTO = (BillDTO) bundle.getSerializable(Keys.KEY_BILL_DTO);
            callAPI(Constant.URL_POST_DOWNLOAD_REPORT_ANNUAL);
        }
        return view;
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getActivity());
        alert.dialogEventListener = this;
        pdfView = (PDFView) view.findViewById(R.id.pdfView);
    }

    public void callAPI(String linkApi) {
        String fileName = Constant.EMPTY;
        try {
            DataSourceConnectionStream connection;
            JSONObject paramsJsonObject = new JSONObject();

            if (annualReport != null) {
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
            } else if (billDTO != null) {
                linkApi = Constant.URL_POST_DOWNLOAD_EBILL;
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
                paramsJsonObject.put(Keys.KEY_BILL_OPTION_HANDLE, "viewPdf");
            }
            connection = new DataSourceConnectionStream(getActivity(), linkApi, paramsJsonObject, Constant.METHOD_POST, true, Constant.PDF_TYPE_VIEW, pdfView);
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
    public void processFinish(String output, String api) {

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
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_BACK, true);
            } else if (Constant.STREAM_RESPONSE_TEXT_SUCCESS.equals(textResult)) {
                this.pdfView.fromStream(inputStream.getResponseStream()).load();
                message = this.getResources().getString(R.string.message_download_success);
                typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, false);
            } else if (Constant.STREAM_RESPONSE_TEXT_FILE_NOT_FOUND.equals(textResult)) {
                message = this.getResources().getString(R.string.message_error_file_not_found);
                typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, false);
            } else if (Constant.STREAM_RESPONSE_LOADED_VIEW.equals(textResult)) {

            } else {
                message = this.getResources().getString(R.string.message_error_system);
                typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
            }
        }
    }

    @Override
    public void OnClickedButtonDialog(boolean isClickOk, String actionDialog, String typeDialog) {
        if (isClickOk) {
            switch (actionDialog) {
                case Constant.ACTION_DIALOG_BACK:
                    getActivity().onBackPressed();
                    break;
            }
        }
    }
}