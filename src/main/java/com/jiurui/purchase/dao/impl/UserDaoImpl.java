package com.jiurui.purchase.dao.impl;

import com.jiurui.purchase.dao.UserDao;
import com.jiurui.purchase.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mark on 15/9/12.
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public User selectByUsername(String name) {
        User user = new User();
        user.setId(1L);
        user.setPassword("123456");
        user.setRole(1);
        user.setUsername("lsy");
        return name.equals("lsy")?user:null;
    }

    @Override
    public int createUser(String username, String password) {
        return 1;
    }

    @Override
    public User selectById(long id) {
        User user = new User();
        user.setId(1L);
        user.setPassword("123456");
        user.setRole(1);
        user.setUsername("lsy");
        return id==1?user:null;
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        user.setPassword(null);
        user.setRole(1);
        user.setUsername("lsy");
        list.add(user);
        User user2 = new User();
        user2.setId(2L);
        user2.setPassword(null);
        user2.setRole(1);
        user2.setUsername("lsy");
        list.add(user2);
        return list;
    }
}
