package com.jiurui.purchase.dao;

import com.jiurui.purchase.model.User;

import java.util.List;

/**
 * Created by mark on 15/9/12.
 */
public interface UserDao {
    User selectByUsername(String name);

    int createUser(String username, String password);

    User selectById(long id);

    List<User> findAll();
}
