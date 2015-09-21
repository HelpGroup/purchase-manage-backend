package com.jiurui.purchase.dao.impl;

import com.jiurui.purchase.dao.TokenDao;
import com.jiurui.purchase.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Created by mark on 15/9/13.
 */
@Repository
public class TokenDaoImpl implements TokenDao {

    @Autowired
    private JdbcTemplate template;

    @Override
    public int persistence(Long id, String token, long time) {
        String sql = "INSERT INTO token VALUES ("+id+",'"+token+"',"+time+")";
        return template.update(sql);
    }

    @Override
    public Token getToken(String value) {
        String sql = "SELECT value,user_id FROM token WHERE value = '"+value+"'";
        Token token = null;
        RowMapper<Token> rm = BeanPropertyRowMapper.newInstance(Token.class);
        try {
            token = template.queryForObject(sql, rm);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return token;
    }

    @Override
    public void clearToken(long time) {
        String sql = "DELETE FROM token WHERE time < " + time;
        template.update(sql);
    }

    @Override
    public int updateToken(String value, long time) {
        String sql = "UPDATE token SET time = "+time+" WHERE value = '"+value+"'";
        return template.update(sql);
    }
}
