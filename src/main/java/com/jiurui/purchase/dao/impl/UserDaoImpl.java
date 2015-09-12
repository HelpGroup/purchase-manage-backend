package com.jiurui.purchase.dao.impl;

import com.jiurui.purchase.dao.UserDao;
import com.jiurui.purchase.model.User;
import org.springframework.stereotype.Repository;

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
}
