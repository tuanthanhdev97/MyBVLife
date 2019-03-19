package app.com.baoviet.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class LoanDTO implements Serializable {
    private String elvAccountNumber;
    private Long elvDate;
    private BigDecimal elvGrossAmount;

    public String getElvAccountNumber() {
        return elvAccountNumber;
    }

    public void setElvAccountNumber(String elvAccountNumber) {
        this.elvAccountNumber = elvAccountNumber;
    }

    public Long getElvDate() {
        return elvDate;
    }

    public void setElvDate(Long elvDate) {
        this.elvDate = elvDate;
    }

    public BigDecimal getElvGrossAmount() {
        return elvGrossAmount;
    }

    public void setElvGrossAmount(BigDecimal elvGrossAmount) {
        this.elvGrossAmount = elvGrossAmount;
    }
}
