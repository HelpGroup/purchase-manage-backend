package com.jiurui.purchase.dao.impl;

import com.jiurui.purchase.dao.TokenDao;
import com.jiurui.purchase.model.Token;
import org.springframework.stereotype.Repository;

/**
 * Created by mark on 15/9/13.
 */
@Repository
public class TokenDaoImpl implements TokenDao {
    @Override
    public int persistence(Long id, String token, long time) {
        return 1;
    }

    @Override
    public Token getToken(String value) {
        Token token = new Token();
        token.setUserId(1L);
        token.setValue("aaa");
        return value.equals("aaa")?token:null;
    }

    @Override
    public void clearToken(long time) {
        String sql = "delete from token where time > " + time;
    }

    @Override
    public int updateToken(String value, long time) {
        return 1;
    }
}
