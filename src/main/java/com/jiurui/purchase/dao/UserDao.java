package com.jiurui.purchase.dao;

import com.jiurui.purchase.model.User;

/**
 * Created by mark on 15/9/12.
 */
public interface UserDao {
    User selectByUsername(String name);
    int createUser(String username, String password);
}
