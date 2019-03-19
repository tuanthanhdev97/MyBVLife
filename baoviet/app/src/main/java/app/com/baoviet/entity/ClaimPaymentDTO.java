package app.com.baoviet.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

public class ClaimPaymentDTO implements Serializable {
    private BigDecimal cltrId;
    private String cltrAccountNumber;
    private Date cltrPaymentDate;
    private BigDecimal cltrAmount;

    private String cltrStatusCode;
    private String cltrStatusDesc;

    public BigDecimal getCltrId() {
        return cltrId;
    }

    public void setCltrId(BigDecimal cltrId) {
        this.cltrId = cltrId;
    }

    public String getCltrAccountNumber() {
        return cltrAccountNumber;
    }

    public void setCltrAccountNumber(String cltrAccountNumber) {
        this.cltrAccountNumber = cltrAccountNumber;
    }

    public Date getCltrPaymentDate() {
        return cltrPaymentDate;
    }

    public void setCltrPaymentDate(Date cltrPaymentDate) {
        this.cltrPaymentDate = cltrPaymentDate;
    }

    public BigDecimal getCltrAmount() {
        return cltrAmount;
    }

    public void setCltrAmount(BigDecimal cltrAmount) {
        this.cltrAmount = cltrAmount;
    }

    public String getCltrStatusCode() {
        return cltrStatusCode;
    }

    public void setCltrStatusCode(String cltrStatusCode) {
        this.cltrStatusCode = cltrStatusCode;
    }

    public String getCltrStatusDesc() {
        return cltrStatusDesc;
    }

    public void setCltrStatusDesc(String cltrStatusDesc) {
        this.cltrStatusDesc = cltrStatusDesc;
    }
}
