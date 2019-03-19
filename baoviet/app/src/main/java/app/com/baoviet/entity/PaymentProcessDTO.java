package app.com.baoviet.entity;

import java.io.Serializable;
import java.util.List;
import java.math.BigDecimal;

public class PaymentProcessDTO implements Serializable {
    private BigDecimal reguTotalAmount;
    private BigDecimal lsumTotalAmount;
    private String maxPremiumProcessedDate;
    private List<PremiumTransDTO> premiumTransList;

    public BigDecimal getReguTotalAmount() {
        return reguTotalAmount;
    }

    public void setReguTotalAmount(BigDecimal reguTotalAmount) {
        this.reguTotalAmount = reguTotalAmount;
    }

    public BigDecimal getLsumTotalAmount() {
        return lsumTotalAmount;
    }

    public void setLsumTotalAmount(BigDecimal lsumTotalAmount) {
        this.lsumTotalAmount = lsumTotalAmount;
    }

    public String getMaxPremiumProcessedDate() {
        return maxPremiumProcessedDate;
    }

    public void setMaxPremiumProcessedDate(String maxPremiumProcessedDate) {
        this.maxPremiumProcessedDate = maxPremiumProcessedDate;
    }

    public List<PremiumTransDTO> getPremiumTransList() {
        return premiumTransList;
    }

    public void setPremiumTransList(List<PremiumTransDTO> premiumTransList) {
        this.premiumTransList = premiumTransList;
    }
}
