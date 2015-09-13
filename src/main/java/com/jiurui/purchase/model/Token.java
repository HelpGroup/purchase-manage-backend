package com.jiurui.purchase.model;

/**
 * Created by mark on 15/9/13.
 */
public class Token {
    public static final long EXPIRATION = 30*60*1000;
    private String value;
    private Long userId;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
