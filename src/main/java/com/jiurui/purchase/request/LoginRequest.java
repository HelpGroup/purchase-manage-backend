package com.jiurui.purchase.request;


import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 登录参数
 *
 */
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 9135562647653705787L;

    @NotNull
    private String username;

    @NotNull
    private String password;

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

}
