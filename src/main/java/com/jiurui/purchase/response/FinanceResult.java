package com.jiurui.purchase.response;

import com.jiurui.purchase.model.FinanceCategory;
import com.jiurui.purchase.model.User;

import java.util.List;

/**
 * Created by mark on 15/9/21.
 */
public class FinanceResult {
    private int status;
    private String message;
    private List<FinanceCategory> chargeList;
    private List<User> users;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FinanceCategory> getChargeList() {
        return chargeList;
    }

    public void setChargeList(List<FinanceCategory> chargeList) {
        this.chargeList = chargeList;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
