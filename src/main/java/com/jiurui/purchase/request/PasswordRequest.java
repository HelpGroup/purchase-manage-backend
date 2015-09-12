package com.jiurui.purchase.request;

import javax.validation.constraints.NotNull;

/**
 * Created by mark on 15/9/12.
 */
public class PasswordRequest {
    @NotNull
    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}