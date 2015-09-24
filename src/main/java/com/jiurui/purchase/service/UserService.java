package com.jiurui.purchase.service;

import com.jiurui.purchase.model.User;
import com.jiurui.purchase.request.CreateUserRequest;

import java.util.List;

/**
 * Created by mark on 15/9/12.
 */
public interface UserService {
    User selectUserByUsername(String name);

    User selectUserById(long id);

    int createUser(CreateUserRequest request);

    List<User> findAll();

    int deleteUser(long id);

    int changePassword(long id, String password);

    List<User> findBranches();
}
