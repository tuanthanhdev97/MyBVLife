package app.com.baoviet.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 5/21/2018.
 */

public class UsersAcctRltnshpDTO implements Serializable {
    private String accountNumber;
    private String reltCode;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getReltCode() {
        return reltCode;
    }

    public void setReltCode(String reltCode) {
        this.reltCode = reltCode;
    }
}
