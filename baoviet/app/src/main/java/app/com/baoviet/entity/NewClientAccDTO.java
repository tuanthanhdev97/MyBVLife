package app.com.baoviet.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class NewClientAccDTO implements Serializable {
    private String clientAccountNumber;
    private String clientName;
    private String clientBusinessPhone;
    private String clientHomePhone;
    private String clientMobilePhone;
    private String clientEmail;
    private String clientIdNumber;
    private String clientTypeCode;
    private String clientAddress;
    private BigDecimal clientId;
    private String clientReltCode;

    public String getClientAccountNumber() {
        return clientAccountNumber;
    }

    public void setClientAccountNumber(String clientAccountNumber) {
        this.clientAccountNumber = clientAccountNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientIdNumber() {
        return clientIdNumber;
    }

    public void setClientIdNumber(String clientIdNumber) {
        this.clientIdNumber = clientIdNumber;
    }

    public String getClientTypeCode() {
        return clientTypeCode;
    }

    public void setClientTypeCode(String clientTypeCode) {
        this.clientTypeCode = clientTypeCode;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public BigDecimal getClientId() {
        return clientId;
    }

    public void setClientId(BigDecimal clientId) {
        this.clientId = clientId;
    }

    public String getClientReltCode() {
        return clientReltCode;
    }

    public void setClientReltCode(String clientReltCode) {
        this.clientReltCode = clientReltCode;
    }
}
