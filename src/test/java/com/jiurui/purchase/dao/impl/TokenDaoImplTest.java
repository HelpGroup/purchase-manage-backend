package com.jiurui.purchase.dao.impl;

import com.jiurui.purchase.dao.BaseTestClass;
import com.jiurui.purchase.dao.TokenDao;
import com.jiurui.purchase.model.Token;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by mark on 15/9/13.
 */
public class TokenDaoImplTest extends BaseTestClass{

    @Autowired
    private TokenDao dao;
    @Test
    public void testPersistence() throws Exception {
        dao.persistence(1L,"aaa",1);
    }

    @Test
    public void testGetToken() throws Exception {
        Token t = dao.getToken("aaad");
        assertEquals(null, t);
    }

    @Test
    public void testClearToken() throws Exception {
        long time = (new Date().getTime()) - Token.EXPIRATION;
        dao.clearToken(time);//清除过期token
    }

    @Test
    public void testUpdateToken() throws Exception {
        assertEquals(1,dao.updateToken("aaa", new Date().getTime()));
    }
}