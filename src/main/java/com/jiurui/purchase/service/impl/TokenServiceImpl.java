package com.jiurui.purchase.service.impl;

import com.jiurui.purchase.dao.TokenDao;
import com.jiurui.purchase.model.Token;
import com.jiurui.purchase.service.TokenService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by mark on 15/9/13.
 */
@Service
public class TokenServiceImpl implements TokenService {

    private final int DEFAULT_LENGTH = 20;
    @Autowired
    private TokenDao dao;
    @Override
    public String createToken() {
        return "aaa";//RandomStringUtils.randomAlphanumeric(DEFAULT_LENGTH);
    }

    @Override
    public int persistence(Long id, String token) {
        return dao.persistence(id,token,new Date().getTime());
    }

    @Override
    public Token getToken(String value) {
        long time = (new Date().getTime()) - Token.EXPIRATION;
        dao.clearToken(time);//清除过期token
        Token token = dao.getToken(value);//检查token
        int r = dao.updateToken(value, new Date().getTime());//跟新token
        return token;
    }
}
