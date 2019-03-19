package app.com.baoviet.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FeesChargesTransDTO implements Serializable {
    private BigDecimal actrId;
    private String actrAccountNumber;
    private Date actrEffectiveDate;
    private Date actrProcessedDate;
    private BigDecimal actrAmountFees;
    private BigDecimal actrAmountCharges;
    private BigDecimal actrAmountTotalFeesCharges;
    private BigDecimal actrAmountOthers;
    private BigDecimal actrAmountTotal;
    private String actrStatusCode;
    private String actrStatusDesc;

    public BigDecimal getActrId() {
        return actrId;
    }

    public void setActrId(BigDecimal actrId) {
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

    public BigDecimal getActrAmountFees() {
        return actrAmountFees;
    }

    public void setActrAmountFees(BigDecimal actrAmountFees) {
        this.actrAmountFees = actrAmountFees;
    }

    public BigDecimal getActrAmountCharges() {
        return actrAmountCharges;
    }

    public void setActrAmountCharges(BigDecimal actrAmountCharges) {
        this.actrAmountCharges = actrAmountCharges;
    }

    public BigDecimal getActrAmountTotalFeesCharges() {
        return actrAmountTotalFeesCharges;
    }

    public void setActrAmountTotalFeesCharges(BigDecimal actrAmountTotalFeesCharges) {
        this.actrAmountTotalFeesCharges = actrAmountTotalFeesCharges;
    }

    public BigDecimal getActrAmountOthers() {
        return actrAmountOthers;
    }

    public void setActrAmountOthers(BigDecimal actrAmountOthers) {
        this.actrAmountOthers = actrAmountOthers;
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
