package app.com.baoviet.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class BenefitDTO implements Serializable {
    //So tien bao hiem tu vong
    private BigDecimal stbhtv;
    //quyen loi tro cap mai tang
    private BigDecimal qltcmt;
    //quyen loi benh nghiem trong
    private BigDecimal qlbnt;
    //quyen loi ho tro tai chinh
    private BigDecimal qlhttc;

    public BigDecimal getStbhtv() {
        return stbhtv;
    }

    public void setStbhtv(BigDecimal stbhtv) {
        this.stbhtv = stbhtv;
    }

    public BigDecimal getQltcmt() {
        return qltcmt;
    }

    public void setQltcmt(BigDecimal qltcmt) {
        this.qltcmt = qltcmt;
    }

    public BigDecimal getQlbnt() {
        return qlbnt;
    }

    public void setQlbnt(BigDecimal qlbnt) {
        this.qlbnt = qlbnt;
    }

    public BigDecimal getQlhttc() {
        return qlhttc;
    }

    public void setQlhttc(BigDecimal qlhttc) {
        this.qlhttc = qlhttc;
    }
}
