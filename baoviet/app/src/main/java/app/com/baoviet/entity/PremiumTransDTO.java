package app.com.baoviet.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PremiumTransDTO implements Serializable {
    private String actrId;
    private String actrAccountNumber;
    private Date actrEffectiveDate;
    private Date actrProcessedDate;
    private BigDecimal actrAmountReguSponsor;
    private BigDecimal actrAmountReguMember;
    private BigDecimal actrAmountReguTotal;
    private BigDecimal actrAmountLsumSponsor;
    private BigDecimal actrAmountLsumMember;
    private BigDecimal actrAmountLsumTotal;
    private BigDecimal actrAmountLsumPolicyholder;
    private BigDecimal actrAmountReguPolicyholder;
    private BigDecimal actrAmountTotal;
    private String actrStatusCode;
    private String actrStatusDesc;

    public String getActrId() {
        return actrId;
    }

    public void setActrId(String actrId) {
        this.actrId = actrId;
    }

    public String getActrAccountNumber() {
        return actrAccountNumber;
    }

    public void setActrAccountNumber(String actrAccountNumber) {
        this.actrAccountNumber = actrAccountNumber;
    }

    public Date getActrEffectiveDate() {
        return actrEffectiveDate;
    }

    public void setActrEffectiveDate(Date actrEffectiveDate) {
        this.actrEffectiveDate = actrEffectiveDate;
    }

    public Date getActrProcessedDate() {
        return actrProcessedDate;
    }

    public void setActrProcessedDate(Date actrProcessedDate) {
        this.actrProcessedDate = actrProcessedDate;
    }

    public BigDecimal getActrAmountReguSponsor() {
        return actrAmountReguSponsor;
    }

    public void setActrAmountReguSponsor(BigDecimal actrAmountReguSponsor) {
        this.actrAmountReguSponsor = actrAmountReguSponsor;
    }

    public BigDecimal getActrAmountReguMember() {
        return actrAmountReguMember;
    }

    public void setActrAmountReguMember(BigDecimal actrAmountReguMember) {
        this.actrAmountReguMember = actrAmountReguMember;
    }

    public BigDecimal getActrAmountReguTotal() {
        return actrAmountReguTotal;
    }

    public void setActrAmountReguTotal(BigDecimal actrAmountReguTotal) {
        this.actrAmountReguTotal = actrAmountReguTotal;
    }

    public BigDecimal getActrAmountLsumSponsor() {
        return actrAmountLsumSponsor;
    }

    public void setActrAmountLsumSponsor(BigDecimal actrAmountLsumSponsor) {
        this.actrAmountLsumSponsor = actrAmountLsumSponsor;
    }

    public BigDecimal getActrAmountLsumMember() {
        return actrAmountLsumMember;
    }

    public void setActrAmountLsumMember(BigDecimal actrAmountLsumMember) {
        this.actrAmountLsumMember = actrAmountLsumMember;
    }

    public BigDecimal getActrAmountLsumTotal() {
        return actrAmountLsumTotal;
    }

    public void setActrAmountLsumTotal(BigDecimal actrAmountLsumTotal) {
        this.actrAmountLsumTotal = actrAmountLsumTotal;
    }

    public BigDecimal getActrAmountLsumPolicyholder() {
        return actrAmountLsumPolicyholder;
    }

    public void setActrAmountLsumPolicyholder(BigDecimal actrAmountLsumPolicyholder) {
        this.actrAmountLsumPolicyholder = actrAmountLsumPolicyholder;
    }

    public BigDecimal getActrAmountReguPolicyholder() {
        return actrAmountReguPolicyholder;
    }

    public void setActrAmountReguPolicyholder(BigDecimal actrAmountReguPolicyholder) {
        this.actrAmountReguPolicyholder = actrAmountReguPolicyholder;
    }

    public BigDecimal getActrAmountTotal() {
        return actrAmountTotal;
    }

    public void setActrAmountTotal(BigDecimal actrAmountTotal) {
        this.actrAmountTotal = actrAmountTotal;
    }

    public String getActrStatusCode() {
        return actrStatusCode;
    }

    public void setActrStatusCode(String actrStatusCode) {
        this.actrStatusCode = actrStatusCode;
    }

    public String getActrStatusDesc() {
        return actrStatusDesc;
    }

    public void setActrStatusDesc(String actrStatusDesc) {
        this.actrStatusDesc = actrStatusDesc;
    }
}
