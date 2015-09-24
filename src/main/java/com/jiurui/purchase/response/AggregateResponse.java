package com.jiurui.purchase.response;

import com.jiurui.purchase.model.User;

import java.util.List;

/**
 * Created by mark on 15/9/17.
 */
public class AggregateResponse {
    private boolean lock = false;

    private List<CategoryResponse> list;

    private List<User> users;

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public List<CategoryResponse> getList() {
        return list;
    }

    public void setList(List<CategoryResponse> list) {
        this.list = list;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
