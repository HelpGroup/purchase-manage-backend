package com.jiurui.purchase.service.impl;

import com.jiurui.purchase.model.User;
import com.jiurui.purchase.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Created by mark on 15/9/12.
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public User selectUserByUsername(String name) {
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
}
