package app.com.baoviet.entity;

import java.io.Serializable;

public class GeneralInfor implements Serializable {
    private String productTypeCode;
    private String latestDate;
    private boolean bvlife;

    public String getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
    }

    public String getLatestDate() {
        return latestDate;
    }

    public void setLatestDate(String latestDate) {
        this.latestDate = latestDate;
    }

    public boolean isBvlife() {
        return bvlife;
    }

    public void setBvlife(boolean bvlife) {
        this.bvlife = bvlife;
    }
}
