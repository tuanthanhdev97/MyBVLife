package app.com.baoviet.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

public class ClaimInfoDTO implements Serializable {
    private BigDecimal claimId;
    private String claimAccountNumber;
    private String claimClientName;
    private Date claimClientDateOfBirth;
    private Date claimEventDate;
    private String claimRiskEventCode;
    private String claimRiskEventDesc;
    private String claimStatusCode;
    private String claimStatusDesc;
    private String claimProductCode;
    private String claimClientBvlName;
    private Date claimClientBvlDateOfBirth;

    public BigDecimal getClaimId() {
        return claimId;
    }

    public void setClaimId(BigDecimal claimId) {
        this.claimId = claimId;
    }

    public String getClaimAccountNumber() {
        return claimAccountNumber;
    }

    public void setClaimAccountNumber(String claimAccountNumber) {
        this.claimAccountNumber = claimAccountNumber;
    }

    public String getClaimClientName() {
        return claimClientName;
    }

    public void setClaimClientName(String claimClientName) {
        this.claimClientName = claimClientName;
    }

    public Date getClaimClientDateOfBirth() {
        return claimClientDateOfBirth;
    }

    public void setClaimClientDateOfBirth(Date claimClientDateOfBirth) {
        this.claimClientDateOfBirth = claimClientDateOfBirth;
    }

    public Date getClaimEventDate() {
        return claimEventDate;
    }

    public void setClaimEventDate(Date claimEventDate) {
        this.claimEventDate = claimEventDate;
    }

    public String getClaimRiskEventCode() {
        return claimRiskEventCode;
    }

    public void setClaimRiskEventCode(String claimRiskEventCode) {
        this.claimRiskEventCode = claimRiskEventCode;
    }

    public String getClaimRiskEventDesc() {
        return claimRiskEventDesc;
    }

    public void setClaimRiskEventDesc(String claimRiskEventDesc) {
        this.claimRiskEventDesc = claimRiskEventDesc;
    }

    public String getClaimStatusCode() {
        return claimStatusCode;
    }

    public void setClaimStatusCode(String claimStatusCode) {
        this.claimStatusCode = claimStatusCode;
    }

    public String getClaimStatusDesc() {
        return claimStatusDesc;
    }

    public void setClaimStatusDesc(String claimStatusDesc) {
        this.claimStatusDesc = claimStatusDesc;
    }

    public String getClaimProductCode() {
        return claimProductCode;
    }

    public void setClaimProductCode(String claimProductCode) {
        this.claimProductCode = claimProductCode;
    }

    public String getClaimClientBvlName() {
        return claimClientBvlName;
    }

    public void setClaimClientBvlName(String claimClientBvlName) {
        this.claimClientBvlName = claimClientBvlName;
    }

    public Date getClaimClientBvlDateOfBirth() {
        return claimClientBvlDateOfBirth;
    }

    public void setClaimClientBvlDateOfBirth(Date claimClientBvlDateOfBirth) {
        this.claimClientBvlDateOfBirth = claimClientBvlDateOfBirth;
    }
}
