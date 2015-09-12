package com.jiurui.purchase.service;

import com.jiurui.purchase.model.User;
import com.jiurui.purchase.request.CreateUserRequest;

/**
 * Created by mark on 15/9/12.
 */
public interface UserService {
    User selectUserByUsername(String name);
    int createUser(CreateUserRequest request);
}
