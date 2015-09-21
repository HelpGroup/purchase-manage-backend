package com.jiurui.purchase.dao.impl;

import com.jiurui.purchase.dao.UserDao;
import com.jiurui.purchase.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mark on 15/9/12.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public User selectByUsername(String name) {
        RowMapper<User> rm = BeanPropertyRowMapper.newInstance(User.class);
        String sql = "SELECT * FROM user WHERE username = '"+ name +"'";
        User user = null;
        try {
            user = template.queryForObject(sql,rm);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int createUser(String username, String password) {
        String sql = "INSERT INTO user(username,password,role_id) VALUES ('"+username+"','"+password+"',2)";
        return template.update(sql);
    }

    @Override
    public User selectById(long id) {
        RowMapper<User> rm = BeanPropertyRowMapper.newInstance(User.class);
        String sql = "SELECT * FROM user WHERE id = "+ id;
        User user = null;
        try {
            user = template.queryForObject(sql,rm);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user ORDER BY role_id ASC";
        List<Map<String, Object>> list = template.queryForList(sql);
        for(Map element : list) {
            User user = new User();
            user.setId((Long)element.get("id"));
            user.setUsername((String)element.get("username"));
            user.setPassword((String)element.get("password"));
            user.setRoleId((int)element.get("role_id"));
            users.add(user);
        }
        return users;
    }

    @Override
    public int deleteUserById(long id) {
        String sql = "DELETE FROM user WHERE id = "+id+"";
        return template.update(sql);
    }

    @Override
    public int updateById(User user) {
        long id = user.getId();
        String password = user.getPassword();
        String sql = "UPDATE user SET password = '"+password+"' WHERE id = "+id;
        return template.update(sql);
    }
}
