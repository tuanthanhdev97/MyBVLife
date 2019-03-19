package app.com.baoviet.entity;

import java.io.Serializable;

public class ClientRegisterNewDTO implements Serializable {
    private String accountNumber;
    private String clientName;
    private String clientIdNumber;
    private String phoneNumber;
    private String email;
    private String userCode;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientIdNumber() {
        return clientIdNumber;
    }

    public void setClientIdNumber(String clientIdNumber) {
        this.clientIdNumber = clientIdNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
