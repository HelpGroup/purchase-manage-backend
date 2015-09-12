package com.jiurui.purchase.service.impl;

import com.jiurui.purchase.dao.UserDao;
import com.jiurui.purchase.model.User;
import com.jiurui.purchase.request.CreateUserRequest;
import com.jiurui.purchase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mark on 15/9/12.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User selectUserByUsername(String name) {
        return userDao.selectByUsername(name);
    }

    @Override
    public User selectUserById(long id) {
        return userDao.selectById(id);
    }

    @Override
    public int createUser(CreateUserRequest request) {
        return userDao.createUser(request.getUsername(), request.getPassword());
    }
}
