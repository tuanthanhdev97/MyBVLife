package app.com.baoviet.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LifeInsuredDTO implements Serializable {
    private BigDecimal clientId;
    private String clienAccountNumber;
    private String clientName;
    private Date clientDateOfBirth;
    private String clientEmail;
    private String clientFaxNumber;
    private String clientBusinessPhone;
    private String clientHomePhone;
    private String clientMobilePhone;
    private String clientSexCode;
    private String clientSexDescription;
    private String clientTypeCode;
    private String clientTypeDescription;
    private String clientOccpCode;
    private String clientOccpDescription;
    private String clientIdentityDoctype;
    private String clientIdnumber;
    private Date clientDateOfIssue;
    private Date clientDateOfExpiry;
    private String clientIssuingAuthority;
    private String clientTaxCode;
    private ClntAddrDTO clntAddrs;

    public BigDecimal getClientId() {
        return clientId;
    }

    public void setClientId(BigDecimal clientId) {
        this.clientId = clientId;
    }

    public String getClienAccountNumber() {
        return clienAccountNumber;
    }

    public void setClienAccountNumber(String clienAccountNumber) {
        this.clienAccountNumber = clienAccountNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getClientDateOfBirth() {
        return clientDateOfBirth;
    }

    public void setClientDateOfBirth(Date clientDateOfBirth) {
        this.clientDateOfBirth = clientDateOfBirth;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientFaxNumber() {
        return clientFaxNumber;
    }

    public void setClientFaxNumber(String clientFaxNumber) {
        this.clientFaxNumber = clientFaxNumber;
    }

    public String getClientBusinessPhone() {
        return clientBusinessPhone;
    }

    public void setClientBusinessPhone(String clientBusinessPhone) {
        this.clientBusinessPhone = clientBusinessPhone;
    }

    public String getClientHomePhone() {
        return clientHomePhone;
    }

    public void setClientHomePhone(String clientHomePhone) {
        this.clientHomePhone = clientHomePhone;
    }

    public String getClientMobilePhone() {
        return clientMobilePhone;
    }

    public void setClientMobilePhone(String clientMobilePhone) {
        this.clientMobilePhone = clientMobilePhone;
    }

    public String getClientSexCode() {
        return clientSexCode;
    }

    public void setClientSexCode(String clientSexCode) {
        this.clientSexCode = clientSexCode;
    }

    public String getClientSexDescription() {
        return clientSexDescription;
    }

    public void setClientSexDescription(String clientSexDescription) {
        this.clientSexDescription = clientSexDescription;
    }

    public String getClientTypeCode() {
        return clientTypeCode;
    }

    public void setClientTypeCode(String clientTypeCode) {
        this.clientTypeCode = clientTypeCode;
    }

    public String getClientTypeDescription() {
        return clientTypeDescription;
    }

    public void setClientTypeDescription(String clientTypeDescription) {
        this.clientTypeDescription = clientTypeDescription;
    }

    public String getClientOccpCode() {
        return clientOccpCode;
    }

    public void setClientOccpCode(String clientOccpCode) {
        this.clientOccpCode = clientOccpCode;
    }

    public String getClientOccpDescription() {
        return clientOccpDescription;
    }

    public void setClientOccpDescription(String clientOccpDescription) {
        this.clientOccpDescription = clientOccpDescription;
    }

    public String getClientIdentityDoctype() {
        return clientIdentityDoctype;
    }

    public void setClientIdentityDoctype(String clientIdentityDoctype) {
        this.clientIdentityDoctype = clientIdentityDoctype;
    }

    public String getClientIdnumber() {
        return clientIdnumber;
    }

    public void setClientIdnumber(String clientIdnumber) {
        this.clientIdnumber = clientIdnumber;
    }

    public Date getClientDateOfIssue() {
        return clientDateOfIssue;
    }

    public void setClientDateOfIssue(Date clientDateOfIssue) {
        this.clientDateOfIssue = clientDateOfIssue;
    }

    public Date getClientDateOfExpiry() {
        return clientDateOfExpiry;
    }

    public void setClientDateOfExpiry(Date clientDateOfExpiry) {
        this.clientDateOfExpiry = clientDateOfExpiry;
    }

    public String getClientIssuingAuthority() {
        return clientIssuingAuthority;
    }

    public void setClientIssuingAuthority(String clientIssuingAuthority) {
        this.clientIssuingAuthority = clientIssuingAuthority;
    }

    public String getClientTaxCode() {
        return clientTaxCode;
    }

    public void setClientTaxCode(String clientTaxCode) {
        this.clientTaxCode = clientTaxCode;
    }

    public ClntAddrDTO getClntAddrs() {
        return clntAddrs;
    }

    public void setClntAddrs(ClntAddrDTO clntAddrs) {
        this.clntAddrs = clntAddrs;
    }
}
