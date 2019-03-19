package app.com.baoviet.entity;

import java.io.Serializable;
import java.util.Date;


public class BillDTO implements Serializable {
    private String billBillNumber;
    private String billAccountNumber;
    private String billPolicyholder;
    private String billInvoiceAmount;
    private Date billFromChargePeriod;
    private Date billToChargePeriod;
    private String billTax;
    private String billAddress;
    private String billOutletNumber;
    private String billOutletName;
    private String billXmlPath;
    private String billViewQuantity;
    private String billDownloadQuantity;
    private Date billEffectDate;
    private String billStatus;
    private Long billCreatedDate;
    private Long billUpdatedDate;
    private String optionHandle;
    private String billPhoneNumber;
    private String billOutletCmpnyName;

    public String getBillBillNumber() {
        return billBillNumber;
    }

    public void setBillBillNumber(String billBillNumber) {
        this.billBillNumber = billBillNumber;
    }

    public String getBillAccountNumber() {
        return billAccountNumber;
    }

    public void setBillAccountNumber(String billAccountNumber) {
        this.billAccountNumber = billAccountNumber;
    }

    public String getBillPolicyholder() {
        return billPolicyholder;
    }

    public void setBillPolicyholder(String billPolicyholder) {
        this.billPolicyholder = billPolicyholder;
    }

    public String getBillInvoiceAmount() {
        return billInvoiceAmount;
    }

    public void setBillInvoiceAmount(String billInvoiceAmount) {
        this.billInvoiceAmount = billInvoiceAmount;
    }

    public Date getBillFromChargePeriod() {
        return billFromChargePeriod;
    }

    public void setBillFromChargePeriod(Date billFromChargePeriod) {
        this.billFromChargePeriod = billFromChargePeriod;
    }

    public Date getBillToChargePeriod() {
        return billToChargePeriod;
    }

    public void setBillToChargePeriod(Date billToChargePeriod) {
        this.billToChargePeriod = billToChargePeriod;
    }

    public String getBillTax() {
        return billTax;
    }

    public void setBillTax(String billTax) {
        this.billTax = billTax;
    }

    public String getBillAddress() {
        return billAddress;
    }

    public void setBillAddress(String billAddress) {
        this.billAddress = billAddress;
    }

    public String getBillOutletNumber() {
        return billOutletNumber;
    }

    public void setBillOutletNumber(String billOutletNumber) {
        this.billOutletNumber = billOutletNumber;
    }

    public String getBillOutletName() {
        return billOutletName;
    }

    public void setBillOutletName(String billOutletName) {
        this.billOutletName = billOutletName;
    }

    public String getBillXmlPath() {
        return billXmlPath;
    }

    public void setBillXmlPath(String billXmlPath) {
        this.billXmlPath = billXmlPath;
    }

    public String getBillViewQuantity() {
        return billViewQuantity;
    }

    public void setBillViewQuantity(String billViewQuantity) {
        this.billViewQuantity = billViewQuantity;
    }

    public String getBillDownloadQuantity() {
        return billDownloadQuantity;
    }

    public void setBillDownloadQuantity(String billDownloadQuantity) {
        this.billDownloadQuantity = billDownloadQuantity;
    }

    public Date getBillEffectDate() {
        return billEffectDate;
    }

    public void setBillEffectDate(Date billEffectDate) {
        this.billEffectDate = billEffectDate;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public Long getBillCreatedDate() {
        return billCreatedDate;
    }

    public void setBillCreatedDate(Long billCreatedDate) {
        this.billCreatedDate = billCreatedDate;
    }

    public Long getBillUpdatedDate() {
        return billUpdatedDate;
    }

    public void setBillUpdatedDate(Long billUpdatedDate) {
        this.billUpdatedDate = billUpdatedDate;
    }

    public String getOptionHandle() {
        return optionHandle;
    }

    public void setOptionHandle(String optionHandle) {
        this.optionHandle = optionHandle;
    }

    public String getBillPhoneNumber() {
        return billPhoneNumber;
    }

    public void setBillPhoneNumber(String billPhoneNumber) {
        this.billPhoneNumber = billPhoneNumber;
    }

    public String getBillOutletCmpnyName() {
        return billOutletCmpnyName;
    }

    public void setBillOutletCmpnyName(String billOutletCmpnyName) {
        this.billOutletCmpnyName = billOutletCmpnyName;
    }
}
