package app.com.baoviet.entity;

/**
 * Created by macosmv on 7/9/18.
 */

public class DataLocal {
    private int id;
    private String username;
    private String password;
    private String api;
    private String params;
    private String response;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return getId() + "||" + getUsername() + "||" + getPassword() + "||" + getApi() + "||" + getParams() + "||" + getResponse();
    }
}
