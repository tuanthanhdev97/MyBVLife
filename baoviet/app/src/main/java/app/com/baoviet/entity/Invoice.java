package app.com.baoviet.entity;

import java.math.BigDecimal;

public class Invoice {
    private String mti;
    private String pan;
    private String processingCode;
    private String tranDateTime;
    private String auditNumber;
    private String clientId;
    private String terminalId;
    private String msgAuthenticationCode;
    private String bbiBarcode;
    private String bbiInvoiceType;
    private String bbiCompanyCode;
    private String bcoCompanyName;
    private String bcoCompanyBvlifeId;
    private String bbiInvoiceNumber;
    private String bbiPromotionId;
    private BigDecimal bbiInvoiceAmountNet;
    private BigDecimal bbiDiscountAmount;
    private String bbiDiscountValue;
    private String bbiChannelFeePerform;
    private String bbiFeeDate;
    private String bbiEffectiveDate;
    private String bbiTransactionCoCode;
    private String bbiTransactionId;
    private String bbiLastUpdatedDate;
    private String bbiTransactionInputer;
    private String bbiTransactionAuthoriser;
    private BigDecimal bbiTransactionAmount;
    private String bbiBankPerform;
    private String bbiPaymentOutlet;
    private String bbiStatus;
    private BigDecimal bbiInvoiceAmount;
    private String bbiAccountNumber;
    private String bbiPolicyHolder;
    private String bbiDataSource;
    private String bbiPaymentInstruction;
    private String bbiFromDate;
    private String bbiToDate;
    private Long dfromDate;
    private Long dtoDate;
    private boolean checkbox;
    private String responseCode;
    private String bbiOutletNumber;

    public String getMti() {
        return mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

    public String getTranDateTime() {
        return tranDateTime;
    }

    public void setTranDateTime(String tranDateTime) {
        this.tranDateTime = tranDateTime;
    }

    public String getAuditNumber() {
        return auditNumber;
    }

    public void setAuditNumber(String auditNumber) {
        this.auditNumber = auditNumber;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getMsgAuthenticationCode() {
        return msgAuthenticationCode;
    }

    public void setMsgAuthenticationCode(String msgAuthenticationCode) {
        this.msgAuthenticationCode = msgAuthenticationCode;
    }

    public String getBbiBarcode() {
        return bbiBarcode;
    }

    public void setBbiBarcode(String bbiBarcode) {
        this.bbiBarcode = bbiBarcode;
    }

    public String getBbiInvoiceType() {
        return bbiInvoiceType;
    }

    public void setBbiInvoiceType(String bbiInvoiceType) {
        this.bbiInvoiceType = bbiInvoiceType;
    }

    public String getBbiCompanyCode() {
        return bbiCompanyCode;
    }

    public void setBbiCompanyCode(String bbiCompanyCode) {
        this.bbiCompanyCode = bbiCompanyCode;
    }

    public String getBcoCompanyName() {
        return bcoCompanyName;
    }

    public void setBcoCompanyName(String bcoCompanyName) {
        this.bcoCompanyName = bcoCompanyName;
    }

    public String getBcoCompanyBvlifeId() {
        return bcoCompanyBvlifeId;
    }

    public void setBcoCompanyBvlifeId(String bcoCompanyBvlifeId) {
        this.bcoCompanyBvlifeId = bcoCompanyBvlifeId;
    }

    public String getBbiInvoiceNumber() {
        return bbiInvoiceNumber;
    }

    public void setBbiInvoiceNumber(String bbiInvoiceNumber) {
        this.bbiInvoiceNumber = bbiInvoiceNumber;
    }

    public String getBbiPromotionId() {
        return bbiPromotionId;
    }

    public void setBbiPromotionId(String bbiPromotionId) {
        this.bbiPromotionId = bbiPromotionId;
    }

    public BigDecimal getBbiInvoiceAmountNet() {
        return bbiInvoiceAmountNet;
    }

    public void setBbiInvoiceAmountNet(BigDecimal bbiInvoiceAmountNet) {
        this.bbiInvoiceAmountNet = bbiInvoiceAmountNet;
    }

    public BigDecimal getBbiDiscountAmount() {
        return bbiDiscountAmount;
    }

    public void setBbiDiscountAmount(BigDecimal bbiDiscountAmount) {
        this.bbiDiscountAmount = bbiDiscountAmount;
    }

    public String getBbiDiscountValue() {
        return bbiDiscountValue;
    }

    public void setBbiDiscountValue(String bbiDiscountValue) {
        this.bbiDiscountValue = bbiDiscountValue;
    }

    public String getBbiChannelFeePerform() {
        return bbiChannelFeePerform;
    }

    public void setBbiChannelFeePerform(String bbiChannelFeePerform) {
        this.bbiChannelFeePerform = bbiChannelFeePerform;
    }

    public String getBbiFeeDate() {
        return bbiFeeDate;
    }

    public void setBbiFeeDate(String bbiFeeDate) {
        this.bbiFeeDate = bbiFeeDate;
    }

    public String getBbiEffectiveDate() {
        return bbiEffectiveDate;
    }

    public void setBbiEffectiveDate(String bbiEffectiveDate) {
        this.bbiEffectiveDate = bbiEffectiveDate;
    }

    public String getBbiTransactionCoCode() {
        return bbiTransactionCoCode;
    }

    public void setBbiTransactionCoCode(String bbiTransactionCoCode) {
        this.bbiTransactionCoCode = bbiTransactionCoCode;
    }

    public String getBbiTransactionId() {
        return bbiTransactionId;
    }

    public void setBbiTransactionId(String bbiTransactionId) {
        this.bbiTransactionId = bbiTransactionId;
    }

    public String getBbiLastUpdatedDate() {
        return bbiLastUpdatedDate;
    }

    public void setBbiLastUpdatedDate(String bbiLastUpdatedDate) {
        this.bbiLastUpdatedDate = bbiLastUpdatedDate;
    }

    public String getBbiTransactionInputer() {
        return bbiTransactionInputer;
    }

    public void setBbiTransactionInputer(String bbiTransactionInputer) {
        this.bbiTransactionInputer = bbiTransactionInputer;
    }

    public String getBbiTransactionAuthoriser() {
        return bbiTransactionAuthoriser;
    }

    public void setBbiTransactionAuthoriser(String bbiTransactionAuthoriser) {
        this.bbiTransactionAuthoriser = bbiTransactionAuthoriser;
    }

    public BigDecimal getBbiTransactionAmount() {
        return bbiTransactionAmount;
    }

    public void setBbiTransactionAmount(BigDecimal bbiTransactionAmount) {
        this.bbiTransactionAmount = bbiTransactionAmount;
    }

    public String getBbiBankPerform() {
        return bbiBankPerform;
    }

    public void setBbiBankPerform(String bbiBankPerform) {
        this.bbiBankPerform = bbiBankPerform;
    }

    public String getBbiPaymentOutlet() {
        return bbiPaymentOutlet;
    }

    public void setBbiPaymentOutlet(String bbiPaymentOutlet) {
        this.bbiPaymentOutlet = bbiPaymentOutlet;
    }

    public String getBbiStatus() {
        return bbiStatus;
    }

    public void setBbiStatus(String bbiStatus) {
        this.bbiStatus = bbiStatus;
    }

    public BigDecimal getBbiInvoiceAmount() {
        return bbiInvoiceAmount;
    }

    public void setBbiInvoiceAmount(BigDecimal bbiInvoiceAmount) {
        this.bbiInvoiceAmount = bbiInvoiceAmount;
    }

    public String getBbiAccountNumber() {
        return bbiAccountNumber;
    }

    public void setBbiAccountNumber(String bbiAccountNumber) {
        this.bbiAccountNumber = bbiAccountNumber;
    }

    public String getBbiPolicyHolder() {
        return bbiPolicyHolder;
    }

    public void setBbiPolicyHolder(String bbiPolicyHolder) {
        this.bbiPolicyHolder = bbiPolicyHolder;
    }

    public String getBbiDataSource() {
        return bbiDataSource;
    }

    public void setBbiDataSource(String bbiDataSource) {
        this.bbiDataSource = bbiDataSource;
    }

    public String getBbiPaymentInstruction() {
        return bbiPaymentInstruction;
    }

    public void setBbiPaymentInstruction(String bbiPaymentInstruction) {
        this.bbiPaymentInstruction = bbiPaymentInstruction;
    }

    public String getBbiFromDate() {
        return bbiFromDate;
    }

    public void setBbiFromDate(String bbiFromDate) {
        this.bbiFromDate = bbiFromDate;
    }

    public String getBbiToDate() {
        return bbiToDate;
    }

    public void setBbiToDate(String bbiToDate) {
        this.bbiToDate = bbiToDate;
    }

    public Long getDfromDate() {
        return dfromDate;
    }

    public void setDfromDate(Long dfromDate) {
        this.dfromDate = dfromDate;
    }

    public Long getDtoDate() {
        return dtoDate;
    }

    public void setDtoDate(Long dtoDate) {
        this.dtoDate = dtoDate;
    }

    public Boolean getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(Boolean checkbox) {
        this.checkbox = checkbox;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getBbiOutletNumber() {
        return bbiOutletNumber;
    }

    public void setBbiOutletNumber(String bbiOutletNumber) {
        this.bbiOutletNumber = bbiOutletNumber;
    }
}
