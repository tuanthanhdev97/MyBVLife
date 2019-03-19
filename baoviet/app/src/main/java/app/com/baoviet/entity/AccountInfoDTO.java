package app.com.baoviet.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AccountInfoDTO implements Serializable {
    private String accAccountNumber;
    private String accAccountNumberOther;
    private Date   accEffectiveDate;
    private Date   accProcessedDate;
    private String accProductCode;
    private String accProductName;
    private String accStatusCode;
    private String accStatusDescription;
    private String accProductTypeCode;
    private String accProductTypeDescription;
    private String accFreqDesc;
    private String accFreqCode;
    private String accRiskTermDesc;
    private String accPremTermDesc;
    private String accGroupNumber;

    private Date accNextBillingDue;
    private BigDecimal accRegularPremium;
    private BigDecimal accInvoicedAmount;
    private BigDecimal ratioLsumPrem;
    private BigDecimal ratioReguPrem;
    private BigDecimal ratioGroupPrem;
    private String accMortalityTypeCode;
    private String accRetyShortName;
    private String accRetireBenefits;
    private String accFirstPav;
    private BigDecimal accRetiredAge;
    private String accRetiredAgeDesc;
    private Date accRiskCeaseDate;

    private BenefitDTO benefitDTO;
    private String shortDesc;
    private List<BenifitTypeDTO> benefitTypeList;

    public String getAccAccountNumber() {
        return accAccountNumber;
    }

    public void setAccAccountNumber(String accAccountNumber) {
        this.accAccountNumber = accAccountNumber;
    }

    public Date getAccEffectiveDate() {
        return accEffectiveDate;
    }

    public void setAccEffectiveDate(Date accEffectiveDate) {
        this.accEffectiveDate = accEffectiveDate;
    }

    public Date getAccProcessedDate() {
        return accProcessedDate;
    }

    public void setAccProcessedDate(Date accProcessedDate) {
        this.accProcessedDate = accProcessedDate;
    }

    public String getAccProductCode() {
        return accProductCode;
    }

    public void setAccProductCode(String accProductCode) {
        this.accProductCode = accProductCode;
    }

    public String getAccProductName() {
        return accProductName;
    }

    public void setAccProductName(String accProductName) {
        this.accProductName = accProductName;
    }

    public String getAccStatusCode() {
        return accStatusCode;
    }

    public void setAccStatusCode(String accStatusCode) {
        this.accStatusCode = accStatusCode;
    }

    public String getAccStatusDescription() {
        return accStatusDescription;
    }

    public void setAccStatusDescription(String accStatusDescription) {
        this.accStatusDescription = accStatusDescription;
    }

    public String getAccProductTypeCode() {
        return accProductTypeCode;
    }

    public void setAccProductTypeCode(String accProductTypeCode) {
        this.accProductTypeCode = accProductTypeCode;
    }

    public String getAccProductTypeDescription() {
        return accProductTypeDescription;
    }

    public void setAccProductTypeDescription(String accProductTypeDescription) {
        this.accProductTypeDescription = accProductTypeDescription;
    }

    public String getAccFreqDesc() {
        return accFreqDesc;
    }

    public void setAccFreqDesc(String accFreqDesc) {
        this.accFreqDesc = accFreqDesc;
    }

    public String getAccFreqCode() {
        return accFreqCode;
    }

    public void setAccFreqCode(String accFreqCode) {
        this.accFreqCode = accFreqCode;
    }

    public String getAccRiskTermDesc() {
        return accRiskTermDesc;
    }

    public void setAccRiskTermDesc(String accRiskTermDesc) {
        this.accRiskTermDesc = accRiskTermDesc;
    }

    public String getAccPremTermDesc() {
        return accPremTermDesc;
    }

    public void setAccPremTermDesc(String accPremTermDesc) {
        this.accPremTermDesc = accPremTermDesc;
    }

    public String getAccGroupNumber() {
        return accGroupNumber;
    }

    public void setAccGroupNumber(String accGroupNumber) {
        this.accGroupNumber = accGroupNumber;
    }

    public Date getAccNextBillingDue() {
        return accNextBillingDue;
    }

    public void setAccNextBillingDue(Date accNextBillingDue) {
        this.accNextBillingDue = accNextBillingDue;
    }

    public BigDecimal getAccRegularPremium() {
        return accRegularPremium;
    }

    public void setAccRegularPremium(BigDecimal accRegularPremium) {
        this.accRegularPremium = accRegularPremium;
    }

    public BigDecimal getAccInvoicedAmount() {
        return accInvoicedAmount;
    }

    public void setAccInvoicedAmount(BigDecimal accInvoicedAmount) {
        this.accInvoicedAmount = accInvoicedAmount;
    }

    public BigDecimal getRatioLsumPrem() {
        return ratioLsumPrem;
    }

    public void setRatioLsumPrem(BigDecimal ratioLsumPrem) {
        this.ratioLsumPrem = ratioLsumPrem;
    }

    public BigDecimal getRatioReguPrem() {
        return ratioReguPrem;
    }

    public void setRatioReguPrem(BigDecimal ratioReguPrem) {
        this.ratioReguPrem = ratioReguPrem;
    }

    public BigDecimal getRatioGroupPrem() {
        return ratioGroupPrem;
    }

    public void setRatioGroupPrem(BigDecimal ratioGroupPrem) {
        this.ratioGroupPrem = ratioGroupPrem;
    }

    public String getAccMortalityTypeCode() {
        return accMortalityTypeCode;
    }

    public void setAccMortalityTypeCode(String accMortalityTypeCode) {
        this.accMortalityTypeCode = accMortalityTypeCode;
    }

    public String getAccRetyShortName() {
        return accRetyShortName;
    }

    public void setAccRetyShortName(String accRetyShortName) {
        this.accRetyShortName = accRetyShortName;
    }

    public String getAccRetireBenefits() {
        return accRetireBenefits;
    }

    public void setAccRetireBenefits(String accRetireBenefits) {
        this.accRetireBenefits = accRetireBenefits;
    }

    public String getAccFirstPav() {
        return accFirstPav;
    }

    public void setAccFirstPav(String accFirstPav) {
        this.accFirstPav = accFirstPav;
    }

    public BigDecimal getAccRetiredAge() {
        return accRetiredAge;
    }

    public void setAccRetiredAge(BigDecimal accRetiredAge) {
        this.accRetiredAge = accRetiredAge;
    }

    public String getAccRetiredAgeDesc() {
        return accRetiredAgeDesc;
    }

    public void setAccRetiredAgeDesc(String accRetiredAgeDesc) {
        this.accRetiredAgeDesc = accRetiredAgeDesc;
    }

    public Date getAccRiskCeaseDate() {
        return accRiskCeaseDate;
    }

    public void setAccRiskCeaseDate(Date accRiskCeaseDate) {
        this.accRiskCeaseDate = accRiskCeaseDate;
    }

    public BenefitDTO getBenefitDTO() {
        return benefitDTO;
    }

    public void setBenefitDTO(BenefitDTO benefitDTO) {
        this.benefitDTO = benefitDTO;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getAccAccountNumberOther() {
        return accAccountNumberOther;
    }

    public void setAccAccountNumberOther(String accAccountNumberOther) {
        this.accAccountNumberOther = accAccountNumberOther;
    }

    public List<BenifitTypeDTO> getBenefitTypeList() {
        return benefitTypeList;
    }

    public void setBenefitTypeList(List<BenifitTypeDTO> benefitTypeList) {
        this.benefitTypeList = benefitTypeList;
    }
}
