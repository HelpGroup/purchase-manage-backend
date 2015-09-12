package com.jiurui.purchase.service;

import com.jiurui.purchase.model.User;

/**
 * Created by mark on 15/9/12.
 */
public interface UserService {
    User selectUserByUsername(String name);
}
