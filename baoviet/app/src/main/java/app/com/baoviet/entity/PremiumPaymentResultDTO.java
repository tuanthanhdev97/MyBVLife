package app.com.baoviet.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 5/15/2018.
 */

public class PremiumPaymentResultDTO implements Serializable {
    private String vpc_AdditionalData;
    private String vpc_Amount;
    private String vpc_Locale;
    private String vpc_BatchNo;
    private String vpc_Command;
    private String vpc_Message;
    private String vpc_Version;
    private String vpc_OrderInfo;
    private String vpc_ReceiptNo;
    private String vpc_Merchant;
    private String vpc_MerchTxnRef;
    private String vpc_AuthorizeId;
    private String vpc_TransactionNo;
    private String vpc_AcqResponseCode;
    private String vpc_ResponseCode;
    private String vpc_CardType;
    private String vpc_CurrencyCode;
    private String vpc_SecureHash;
    private String pathResult;

    public PremiumPaymentResultDTO() {

    }

    public String getVpc_AdditionalData() {
        return vpc_AdditionalData;
    }

    public void setVpc_AdditionalData(String vpc_AdditionalData) {
        this.vpc_AdditionalData = vpc_AdditionalData;
    }

    public String getVpc_Amount() {
        return vpc_Amount;
    }

    public void setVpc_Amount(String vpc_Amount) {
        this.vpc_Amount = vpc_Amount;
    }

    public String getVpc_BatchNo() {
        return vpc_BatchNo;
    }

    public void setVpc_BatchNo(String vpc_BatchNo) {
        this.vpc_BatchNo = vpc_BatchNo;
    }

    public String getVpc_Command() {
        return vpc_Command;
    }

    public void setVpc_Command(String vpc_Command) {
        this.vpc_Command = vpc_Command;
    }

    public String getVpc_CurrencyCode() {
        return vpc_CurrencyCode;
    }

    public void setVpc_CurrencyCode(String vpc_CurrencyCode) {
        this.vpc_CurrencyCode = vpc_CurrencyCode;
    }

    public String getVpc_Locale() {
        return vpc_Locale;
    }

    public void setVpc_Locale(String vpc_Locale) {
        this.vpc_Locale = vpc_Locale;
    }

    public String getVpc_MerchTxnRef() {
        return vpc_MerchTxnRef;
    }

    public void setVpc_MerchTxnRef(String vpc_MerchTxnRef) {
        this.vpc_MerchTxnRef = vpc_MerchTxnRef;
    }

    public String getVpc_Merchant() {
        return vpc_Merchant;
    }

    public void setVpc_Merchant(String vpc_Merchant) {
        this.vpc_Merchant = vpc_Merchant;
    }

    public String getVpc_OrderInfo() {
        return vpc_OrderInfo;
    }

    public void setVpc_OrderInfo(String vpc_OrderInfo) {
        this.vpc_OrderInfo = vpc_OrderInfo;
    }

    public String getVpc_ResponseCode() {
        return vpc_ResponseCode;
    }

    public void setVpc_ResponseCode(String vpc_ResponseCode) {
        this.vpc_ResponseCode = vpc_ResponseCode;
    }

    public String getVpc_TransactionNo() {
        return vpc_TransactionNo;
    }

    public void setVpc_TransactionNo(String vpc_TransactionNo) {
        this.vpc_TransactionNo = vpc_TransactionNo;
    }

    public String getVpc_Version() {
        return vpc_Version;
    }

    public void setVpc_Version(String vpc_Version) {
        this.vpc_Version = vpc_Version;
    }

    public String getVpc_SecureHash() {
        return vpc_SecureHash;
    }

    public void setVpc_SecureHash(String vpc_SecureHash) {
        this.vpc_SecureHash = vpc_SecureHash;
    }

    public String getVpc_CardType() {
        return vpc_CardType;
    }

    public void setVpc_CardType(String vpc_CardType) {
        this.vpc_CardType = vpc_CardType;
    }

    public String getVpc_Message() {
        return vpc_Message;
    }

    public void setVpc_Message(String vpc_Message) {
        this.vpc_Message = vpc_Message;
    }

    public String getVpc_ReceiptNo() {
        return vpc_ReceiptNo;
    }

    public void setVpc_ReceiptNo(String vpc_ReceiptNo) {
        this.vpc_ReceiptNo = vpc_ReceiptNo;
    }

    public String getVpc_AuthorizeId() {
        return vpc_AuthorizeId;
    }

    public void setVpc_AuthorizeId(String vpc_AuthorizeId) {
        this.vpc_AuthorizeId = vpc_AuthorizeId;
    }

    public String getVpc_AcqResponseCode() {
        return vpc_AcqResponseCode;
    }

    public void setVpc_AcqResponseCode(String vpc_AcqResponseCode) {
        this.vpc_AcqResponseCode = vpc_AcqResponseCode;
    }

    public String getPathResult() {
        return pathResult;
    }

    public void setPathResult(String pathResult) {
        this.pathResult = pathResult;
    }
}
