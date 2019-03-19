package app.com.baoviet.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.com.baoviet.R;
import app.com.baoviet.constant.Constant;
import app.com.baoviet.constant.Keys;
import app.com.baoviet.datasource.DataSourceConnection;
import app.com.baoviet.entity.PremiumPaymentResultDTO;
import app.com.baoviet.entity.ResponseStream;
import app.com.baoviet.interfaceclass.AsyncResponse;
import app.com.baoviet.interfaceclass.DialogEventListener;
import app.com.baoviet.utility.AlertDialogCustom;
import app.com.baoviet.utility.StringUtil;


public class PaymentNapasFragment extends Fragment implements DialogEventListener, AsyncResponse {
    private WebView wbPaymentNapas;
    private String webLinkNapas;
    private String backUrlReturn;
    private String phoneNumber;
    private String fullName;
    private List<String> listAccount;

    AlertDialogCustom alert;
    String message = Constant.EMPTY;
    String typeDialog = Constant.EMPTY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_napas, container, false);
        webLinkNapas = getArguments().getString(Keys.KEY_WEB_LINK_NAPAS);
        backUrlReturn = getArguments().getString(Keys.KEY_WEB_BACK_RETURN_URL);
        if (!StringUtil.isNullOrEmpty(Constant.TOKEN)) {
            phoneNumber = getArguments().getString(Keys.KEY_PHONE_NUMBER);
            fullName = getArguments().getString(Keys.KEY_FULLNAME);
            listAccount = getArguments().getStringArrayList(Keys.KEY_LIST_ACCOUNT_NUMBER);
        }
        initInterface(view);
        return view;
    }

    public void initInterface(View view) {
        alert = new AlertDialogCustom(getActivity());
        alert.dialogEventListener = this;
        wbPaymentNapas = (WebView) view.findViewById(R.id.wbPaymentNapas);
        final ProgressDialog dialog = ProgressDialog.show(getContext(), "", Constant.LOADING_PROCESS, true);


        if (!StringUtil.isNullOrEmpty(webLinkNapas) && !StringUtil.isNullOrEmpty(backUrlReturn)) {
            WebViewClient webViewClient = new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    dialog.show();
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith(backUrlReturn)) {
                        savePaymentToDb(url);
                        return true;
                    } else {
                        return false;
                    }
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    // Loading finished for URL
                    dialog.dismiss();
                }
            };
            wbPaymentNapas.getSettings().setJavaScriptEnabled(true);
            wbPaymentNapas.getSettings().setLoadWithOverviewMode(true);
            wbPaymentNapas.getSettings().setUseWideViewPort(true);
            wbPaymentNapas.setWebViewClient(webViewClient);
            wbPaymentNapas.loadUrl(webLinkNapas);
        } else {
            message = getResources().getString(R.string.message_error_system);
            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
        }

    }

    public String getJsonPaymentProcessObjFromLink(String url) {
        String jsonParams = Constant.EMPTY;
        Gson gson = new Gson();
        PremiumPaymentResultDTO paymentProcess = new PremiumPaymentResultDTO();
        String paramsStr = url.split("\\?")[1];
        String[] paramsValue = paramsStr.split("&");
        for (String valueParam : paramsValue) {
            String[] paramsPair = valueParam.split("=");
            String value = paramsPair[1];
            switch (paramsPair[0]) {
                case Constant.VPC_ADDITIONALDATA:
                    paymentProcess.setVpc_AdditionalData(value);
                    break;
                case Constant.VPC_AMOUNT:
                    paymentProcess.setVpc_Amount(value);
                    break;
                case Constant.VPC_LOCATE:
                    paymentProcess.setVpc_Locale(value);
                    break;
                case Constant.VPC_BATCHNO:
                    paymentProcess.setVpc_BatchNo(value);
                    break;
                case Constant.VPC_COMMAND:
                    paymentProcess.setVpc_Command(value);
                    break;
                case Constant.VPC_MESSAGE:
                    paymentProcess.setVpc_Message(value);
                    break;
                case Constant.VPC_VERSION:
                    paymentProcess.setVpc_Version(value);
                    break;
                case Constant.VPC_ORDERINFOR:
                    paymentProcess.setVpc_OrderInfo(value);
                    break;
                case Constant.VPC_RECEIPTNO:
                    paymentProcess.setVpc_ReceiptNo(value);
                    break;
                case Constant.VPC_MERCHANT:
                    paymentProcess.setVpc_Merchant(value);
                    break;
                case Constant.VPC_MERCHTXNREF:
                    paymentProcess.setVpc_MerchTxnRef(value);
                    break;
                case Constant.VPC_AUTHORIZEED:
                    paymentProcess.setVpc_AuthorizeId(value);
                    break;
                case Constant.VPC_TRANSACTIONNO:
                    paymentProcess.setVpc_TransactionNo(value);
                    break;
                case Constant.VPC_ACQRESPONSECODE:
                    paymentProcess.setVpc_AcqResponseCode(value);
                    break;
                case Constant.VPC_RESPONSECODE:
                    paymentProcess.setVpc_ResponseCode(value);
                    break;
                case Constant.VPC_CARDTYPE:
                    paymentProcess.setVpc_CardType(value);
                    break;
                case Constant.VPC_CURRENTCYCODE:
                    paymentProcess.setVpc_CurrencyCode(value);
                    break;
                case Constant.VPC_SECUREHASH:
                    paymentProcess.setVpc_SecureHash(value);
                    break;
                case Constant.PATH_RESULT:
                    paymentProcess.setPathResult(value);
                    break;
            }
        }
        return gson.toJson(paymentProcess);
    }

    public void savePaymentToDb(String url) {
        try {
            String jsonParams = getJsonPaymentProcessObjFromLink(url);
            DataSourceConnection connection;
            Activity activity = getActivity();
            if (isAdded() && activity != null) {
                connection = new DataSourceConnection(activity, Constant.URL_PREMINUM_RESULT, jsonParams, Constant.METHOD_POST);
                connection.delegate = this;
                connection.execute();
            }

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
                if (Constant.ERROR_SERVER.equals(result)) {
                    message = getResources().getString(R.string.message_error_server);
                    typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                    alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                }
                JSONObject jsonObject = new JSONObject(result);
                int responseStatus = jsonObject.getInt(Keys.KEY_RESPONSE_STATUS);
                String responseMessage = jsonObject.getString(Keys.KEY_RESPONSE_MESSAGE);
                if (urlApi.equals(Constant.URL_PREMINUM_RESULT)) {
                    if (Constant.RESPONSE_CODE_200 == responseStatus) {
                        String statusPayment = jsonObject.getString(Keys.KEY_OBJECT);
                        if (!StringUtil.isNullOrEmpty(statusPayment)) {
                            String messageResponse = getMessageStatusPreminumPayment(statusPayment);
                            if (Constant.STR_INT_0.equals(statusPayment)) {
                                typeDialog = Constant.TYPE_ALERT_DIALOG_INFOR;
                            } else {
                                typeDialog = Constant.TYPE_ALERT_DIALOG_ERROR;
                            }
                            message = messageResponse;
                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DONE_PAYMENT, true);
                        } else {
                            message = getResources().getString(R.string.message_error_system);
                            typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                            alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);
                        }

                    } else if (Constant.RESPONSE_CODE_412 == responseStatus) {
                        message = responseMessage;
                        typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
                        alert.showDialog(message, typeDialog, Constant.ACTION_DIALOG_DEFAULT, true);

                    } else {
                        message = getResources().getString(R.string.message_error_system);
                        typeDialog = Constant.TYPE_ALERT_DIALOG_EXCEPTION;
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

    public String getMessageStatusPreminumPayment(String status) {
        String responseDes = Constant.EMPTY;
        switch (status) {
            case "-2":
                responseDes = "Giao dịch không thành công - lỗi gạch nợ";
                break;
            case "-1":
                responseDes = "Giao dịch không thành công - sai mã bảo mật";
                break;
            case "0":
//                status = 'success';
                responseDes = "Giao dịch thanh toán thành công. Hệ thống sẽ gửi tin nhắn thông báo tới số điện thoại của Quý khách";
                break;
            case "1":
                responseDes = "Ngân hàng từ chối thanh toán: thẻ/tài khoản bị khóa";
                break;
            case "2":
                responseDes = "Thông tin thẻ không hợp lệ";
                break;
            case "3":
                responseDes = "Thẻ hết hạn";
                break;
            case "4":
                responseDes = "Lỗi người mua hàng: Quá số lần cho phép. (Sai OTP, quá hạn mức trong ngày)";
                break;
            case "5":
                responseDes = "Không có trả lời của Ngân hàng";
                break;
            case "6":
                responseDes = "Lỗi giao tiếp với Ngân hàng";
                break;
            case "7":
                responseDes = "Tài khoản không đủ tiền";
                break;
            case "8":
                responseDes = "Lỗi checksum dữ liệu";
                break;
            case "9":
                responseDes = "Kiểu giao dịch không được hỗ trợ";
                break;
            case "10":
                responseDes = "Lỗi Khác";
                break;
            case "11":
                responseDes = "Giao dịch chưa được xác thực OTP";
                break;
            case "12":
                responseDes = "Giao dịch không thành công, thẻ vượt quá hạn mức trong ngày";
                break;
            case "13":
                responseDes = "Thẻ chưa đăng ký dịch vụ giao dịch qua internet";
                break;
            case "14":
                responseDes = "Sai OTP";
                break;
            case "15":
                responseDes = "Sai mật khẩu";
                break;
            case "16":
                responseDes = "Sai tên chủ thẻ";
                break;
            case "17":
                responseDes = "Sai số thẻ";
                break;
            case "18":
                responseDes = "Sai ngày hiệu lực thẻ (sai ngày phát hành)";
                break;
            case "19":
                responseDes = "Sai ngày hiệu lực thẻ (sai ngày hết hạn)";
                break;
            case "20":
                responseDes = "OTP Timeout";
                break;
            case "22":
                responseDes = "Chưa xác thực thông tin thẻ";
                break;
            case "23":
                responseDes = "Không đủ điều kiện thanh toán (thẻ/tài khoản không hợp lệ hoặc TK không đủ số dư).";
                break;
            case "24":
                responseDes = "Giao dịch không thành công, số tiền giao dịch vượt quá hạn mức 1 lần thanh toán.";
                break;
            case "25":
                responseDes = "Giao dịch không thành công, số tiền giao dịch vượt hạn mức thanh toán.";
                break;
            case "26":
                responseDes = "Giao dịch chờ xác nhận từ Ngân hàng.";
                break;
            case "27":
                responseDes = "Sai thông tin xác thực ( áp dụng cho các NH thực hiện xác thực qua Internet Banking của NH)";
                break;
            case "28":
                responseDes = "Timeout giao dịch";
                break;
            case "29":
                responseDes = "Lỗi xử lý giao dịch tại hệ thống NHPH";
                break;
            default:
                responseDes = "Giao dịch không thành công - lỗi hệ thống";
                break;
        }
        return responseDes;
    }


    @Override
    public void processFinishStream(ResponseStream inputStream) {

    }

    @Override
    public void OnClickedButtonDialog(boolean isClickOk, String actionDialog, String typeDialog) {
        if (isClickOk) {
            switch (actionDialog) {
                case Constant.ACTION_DIALOG_DONE_PAYMENT:
                    Bundle bundleNapas = new Bundle();
                    PaymentFragment fr = new PaymentFragment();
                    if (!StringUtil.isNullOrEmpty(Constant.TOKEN)) {
                        bundleNapas.putString(Keys.KEY_PHONE_NUMBER, phoneNumber);
                        bundleNapas.putString(Keys.KEY_FULLNAME, fullName);
                        bundleNapas.putStringArrayList(Keys.KEY_LIST_ACCOUNT_NUMBER, (ArrayList<String>) listAccount);
                        fr.setArguments(bundleNapas);
                    }
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.frameContent, fr);
                    fragmentTransaction.commit();
                    break;
            }
        }
    }
}