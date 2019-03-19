package app.com.baoviet.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import app.com.baoviet.interfaceclass.GrantedAuthority;
import app.com.baoviet.interfaceclass.UserDetails;

public class UserDTO implements UserDetails, Serializable {
    private BigDecimal userId;
    private String userCode;
    private String fullName;
    private String email;
    private String address;
    private String mobifone;
    private String userTypeCode;
    private String userAlias;
    private List<String> roles;
    private boolean admin;
    private String[] roleMenus;
    private List<String> accounts;
    private List<String> groups;
    private String accountNumber;
    private String groupNumber;
    private List<GrantedAuthority> authorities;
    private boolean enabled;
    //private Date lastPasswordResetDate;
    private String password;
    private String token;
    private boolean group;
    private boolean account;
    private String clientIdentityDoctype;
    private String userClientIdnumber;
    // set cho BVLife
    private String accountOtherNumber;
    private List<String> accountsOtherGroupList;

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobifone() {
        return mobifone;
    }

    public void setMobifone(String mobifone) {
        this.mobifone = mobifone;
    }

    public String getUserTypeCode() {
        return userTypeCode;
    }

    public void setUserTypeCode(String userTypeCode) {
        this.userTypeCode = userTypeCode;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String[] getRoleMenus() {
        return roleMenus;
    }

    public void setRoleMenus(String[] roleMenus) {
        this.roleMenus = roleMenus;
    }

    public List<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClientIdentityDoctype() {
        return clientIdentityDoctype;
    }

    public void setClientIdentityDoctype(String clientIdentityDoctype) {
        this.clientIdentityDoctype = clientIdentityDoctype;
    }

    public String getUserClientIdnumber() {
        return userClientIdnumber;
    }

    public void setUserClientIdnumber(String userClientIdnumber) {
        this.userClientIdnumber = userClientIdnumber;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public boolean isAccount() {
        return account;
    }

    public void setAccount(boolean account) {
        this.account = account;
    }

    public String getAccountOtherNumber() {
        return accountOtherNumber;
    }

    public void setAccountOtherNumber(String accountOtherNumber) {
        this.accountOtherNumber = accountOtherNumber;
    }

    public List<String> getAccountsOtherGroupList() {
        return accountsOtherGroupList;
    }

    public void setAccountsOtherGroupList(List<String> accountsOtherGroupList) {
        this.accountsOtherGroupList = accountsOtherGroupList;
    }
}
