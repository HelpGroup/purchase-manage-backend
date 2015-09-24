package com.jiurui.purchase.service.impl;

import com.jiurui.purchase.dao.UserDao;
import com.jiurui.purchase.model.Role;
import com.jiurui.purchase.model.User;
import com.jiurui.purchase.request.CreateUserRequest;
import com.jiurui.purchase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public int deleteUser(long id) {
        User user = selectUserById(id);
        if(user == null) return 0;
        if(user.getRoleId().equals(Role.ADMIN)) return -1;
        return userDao.deleteUserById(id);
    }

    @Override
    public int changePassword(long id, String password) {
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        return userDao.updateById(user);
    }

    @Override
    public List<User> findBranches() {
        return userDao.findBranches();
    }
}
