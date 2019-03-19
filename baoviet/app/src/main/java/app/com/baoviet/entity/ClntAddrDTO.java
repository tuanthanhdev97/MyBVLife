package app.com.baoviet.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ClntAddrDTO implements Serializable {
    private BigDecimal addrId;
    private BigDecimal addrClientId;
    private String addrIsPrimary;
    private String addrIsPrimaryType;
    private String addrAddress;
    private String addrFullAddress;
    private String addrSuburb;
    private String addrCity;
    private String addrState;
    private String addrAddressTypeCode;
    private String addrAddressTypeDesc;

    public BigDecimal getAddrId() {
        return addrId;
    }

    public void setAddrId(BigDecimal addrId) {
        this.addrId = addrId;
    }

    public BigDecimal getAddrClientId() {
        return addrClientId;
    }

    public void setAddrClientId(BigDecimal addrClientId) {
        this.addrClientId = addrClientId;
    }

    public String getAddrIsPrimary() {
        return addrIsPrimary;
    }

    public void setAddrIsPrimary(String addrIsPrimary) {
        this.addrIsPrimary = addrIsPrimary;
    }

    public String getAddrIsPrimaryType() {
        return addrIsPrimaryType;
    }

    public void setAddrIsPrimaryType(String addrIsPrimaryType) {
        this.addrIsPrimaryType = addrIsPrimaryType;
    }

    public String getAddrAddress() {
        return addrAddress;
    }

    public void setAddrAddress(String addrAddress) {
        this.addrAddress = addrAddress;
    }

    public String getAddrFullAddress() {
        return addrFullAddress;
    }

    public void setAddrFullAddress(String addrFullAddress) {
        this.addrFullAddress = addrFullAddress;
    }

    public String getAddrSuburb() {
        return addrSuburb;
    }

    public void setAddrSuburb(String addrSuburb) {
        this.addrSuburb = addrSuburb;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    public String getAddrState() {
        return addrState;
    }

    public void setAddrState(String addrState) {
        this.addrState = addrState;
    }

    public String getAddrAddressTypeCode() {
        return addrAddressTypeCode;
    }

    public void setAddrAddressTypeCode(String addrAddressTypeCode) {
        this.addrAddressTypeCode = addrAddressTypeCode;
    }

    public String getAddrAddressTypeDesc() {
        return addrAddressTypeDesc;
    }

    public void setAddrAddressTypeDesc(String addrAddressTypeDesc) {
        this.addrAddressTypeDesc = addrAddressTypeDesc;
    }
}
