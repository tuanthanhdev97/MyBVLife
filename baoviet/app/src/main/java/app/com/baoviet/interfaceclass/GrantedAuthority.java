package app.com.baoviet.interfaceclass;

import java.io.Serializable;

public class GrantedAuthority implements Serializable {
    private String authority;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
    //    String getAuthority();
}
