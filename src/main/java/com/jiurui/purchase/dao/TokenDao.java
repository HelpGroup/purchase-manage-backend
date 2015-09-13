package com.jiurui.purchase.dao;

import com.jiurui.purchase.model.Token;

/**
 * Created by mark on 15/9/13.
 */
public interface TokenDao {

    int persistence(Long id, String token, long time);

    Token getToken(String value);

    void clearToken(long time);

    int updateToken(String value, long time);
}
