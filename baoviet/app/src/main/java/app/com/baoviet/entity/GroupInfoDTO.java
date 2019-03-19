package app.com.baoviet.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class GroupInfoDTO implements Serializable {

    private String grpGroupNumber;
    private Date grpEffectiveDate;
    private Date grpProcessedDate;
    private String grpProductCode;
    private String grpProductName;
    private String grpStatusCode;
    private String grpStatusDescription;
    private String grpProductTypeCode;
    private String grpProductTypeDescription;
    private String grpFreqDesc;
    private String shortDesc;
    private String grpMortalityTypeCode;
    private String grpRetireBenefits;
    private String grpFirstPav;
    private BigDecimal grpRetiredAge;
    private String grpRetiredAgeDesc;
    private Date grpRiskCeaseDate;

    private BenefitDTO benefitDTO;
    private List<BenifitTypeDTO> benefitTypeList;

    public String getGrpGroupNumber() {
        return grpGroupNumber;
    }

    public void setGrpGroupNumber(String grpGroupNumber) {
        this.grpGroupNumber = grpGroupNumber;
    }

    public Date getGrpEffectiveDate() {
        return grpEffectiveDate;
    }

    public void setGrpEffectiveDate(Date grpEffectiveDate) {
        this.grpEffectiveDate = grpEffectiveDate;
    }

    public Date getGrpProcessedDate() {
        return grpProcessedDate;
    }

    public void setGrpProcessedDate(Date grpProcessedDate) {
        this.grpProcessedDate = grpProcessedDate;
    }

    public String getGrpProductCode() {
        return grpProductCode;
    }

    public void setGrpProductCode(String grpProductCode) {
        this.grpProductCode = grpProductCode;
    }

    public String getGrpProductName() {
        return grpProductName;
    }

    public void setGrpProductName(String grpProductName) {
        this.grpProductName = grpProductName;
    }

    public String getGrpStatusCode() {
        return grpStatusCode;
    }

    public void setGrpStatusCode(String grpStatusCode) {
        this.grpStatusCode = grpStatusCode;
    }

    public String getGrpStatusDescription() {
        return grpStatusDescription;
    }

    public void setGrpStatusDescription(String grpStatusDescription) {
        this.grpStatusDescription = grpStatusDescription;
    }

    public String getGrpProductTypeCode() {
        return grpProductTypeCode;
    }

    public void setGrpProductTypeCode(String grpProductTypeCode) {
        this.grpProductTypeCode = grpProductTypeCode;
    }

    public String getGrpProductTypeDescription() {
        return grpProductTypeDescription;
    }

    public void setGrpProductTypeDescription(String grpProductTypeDescription) {
        this.grpProductTypeDescription = grpProductTypeDescription;
    }

    public String getGrpFreqDesc() {
        return grpFreqDesc;
    }

    public void setGrpFreqDesc(String grpFreqDesc) {
        this.grpFreqDesc = grpFreqDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getGrpMortalityTypeCode() {
        return grpMortalityTypeCode;
    }

    public void setGrpMortalityTypeCode(String grpMortalityTypeCode) {
        this.grpMortalityTypeCode = grpMortalityTypeCode;
    }

    public String getGrpRetireBenefits() {
        return grpRetireBenefits;
    }

    public void setGrpRetireBenefits(String grpRetireBenefits) {
        this.grpRetireBenefits = grpRetireBenefits;
    }

    public String getGrpFirstPav() {
        return grpFirstPav;
    }

    public void setGrpFirstPav(String grpFirstPav) {
        this.grpFirstPav = grpFirstPav;
    }

    public BigDecimal getGrpRetiredAge() {
        return grpRetiredAge;
    }

    public void setGrpRetiredAge(BigDecimal grpRetiredAge) {
        this.grpRetiredAge = grpRetiredAge;
    }

    public String getGrpRetiredAgeDesc() {
        return grpRetiredAgeDesc;
    }

    public void setGrpRetiredAgeDesc(String grpRetiredAgeDesc) {
        this.grpRetiredAgeDesc = grpRetiredAgeDesc;
    }

    public Date getGrpRiskCeaseDate() {
        return grpRiskCeaseDate;
    }

    public void setGrpRiskCeaseDate(Date grpRiskCeaseDate) {
        this.grpRiskCeaseDate = grpRiskCeaseDate;
    }

    public BenefitDTO getBenefitDTO() {
        return benefitDTO;
    }

    public void setBenefitDTO(BenefitDTO benefitDTO) {
        this.benefitDTO = benefitDTO;
    }

    public List<BenifitTypeDTO> getBenefitTypeList() {
        return benefitTypeList;
    }

    public void setBenefitTypeList(List<BenifitTypeDTO> benefitTypeList) {
        this.benefitTypeList = benefitTypeList;
    }
}
