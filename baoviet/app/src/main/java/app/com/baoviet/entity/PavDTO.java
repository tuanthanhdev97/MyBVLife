package app.com.baoviet.entity;

import java.io.Serializable;
import java.util.List;
import java.math.BigDecimal;

public class PavDTO implements Serializable {
    private String pavMonth;
    private Long pavDate;
    private BigDecimal pavUnitsO;
    private BigDecimal pavPriceO;
    private BigDecimal pavValueO;
    private BigDecimal pavUnitsS;
    private BigDecimal pavPriceS;
    private BigDecimal pavValueS;
    private BigDecimal pavUnitsM;
    private BigDecimal pavPriceM;
    private BigDecimal pavValueM;
    private BigDecimal pavTotal;

    private int pavRatioM;
    private int pavRatioS;

    private List<PavDTO> pavDetails;

    public String getPavMonth() {
        return pavMonth;
    }

    public void setPavMonth(String pavMonth) {
        this.pavMonth = pavMonth;
    }

    public Long getPavDate() {
        return pavDate;
    }

    public void setPavDate(Long pavDate) {
        this.pavDate = pavDate;
    }

    public BigDecimal getPavUnitsO() {
        return pavUnitsO;
    }

    public void setPavUnitsO(BigDecimal pavUnitsO) {
        this.pavUnitsO = pavUnitsO;
    }

    public BigDecimal getPavPriceO() {
        return pavPriceO;
    }

    public void setPavPriceO(BigDecimal pavPriceO) {
        this.pavPriceO = pavPriceO;
    }

    public BigDecimal getPavValueO() {
        return pavValueO;
    }

    public void setPavValueO(BigDecimal pavValueO) {
        this.pavValueO = pavValueO;
    }

    public BigDecimal getPavUnitsS() {
        return pavUnitsS;
    }

    public void setPavUnitsS(BigDecimal pavUnitsS) {
        this.pavUnitsS = pavUnitsS;
    }

    public BigDecimal getPavPriceS() {
        return pavPriceS;
    }

    public void setPavPriceS(BigDecimal pavPriceS) {
        this.pavPriceS = pavPriceS;
    }

    public BigDecimal getPavValueS() {
        return pavValueS;
    }

    public void setPavValueS(BigDecimal pavValueS) {
        this.pavValueS = pavValueS;
    }

    public BigDecimal getPavUnitsM() {
        return pavUnitsM;
    }

    public void setPavUnitsM(BigDecimal pavUnitsM) {
        this.pavUnitsM = pavUnitsM;
    }

    public BigDecimal getPavPriceM() {
        return pavPriceM;
    }

    public void setPavPriceM(BigDecimal pavPriceM) {
        this.pavPriceM = pavPriceM;
    }

    public BigDecimal getPavValueM() {
        return pavValueM;
    }

    public void setPavValueM(BigDecimal pavValueM) {
        this.pavValueM = pavValueM;
    }

    public BigDecimal getPavTotal() {
        return pavTotal;
    }

    public void setPavTotal(BigDecimal pavTotal) {
        this.pavTotal = pavTotal;
    }

    public int getPavRatioM() {
        return pavRatioM;
    }

    public void setPavRatioM(int pavRatioM) {
        this.pavRatioM = pavRatioM;
    }

    public int getPavRatioS() {
        return pavRatioS;
    }

    public void setPavRatioS(int pavRatioS) {
        this.pavRatioS = pavRatioS;
    }

    public List<PavDTO> getPavDetails() {
        return pavDetails;
    }

    public void setPavDetails(List<PavDTO> pavDetails) {
        this.pavDetails = pavDetails;
    }
}
