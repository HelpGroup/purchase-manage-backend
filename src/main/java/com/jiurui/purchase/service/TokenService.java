package com.jiurui.purchase.service;

import com.jiurui.purchase.model.Token;

/**
 * Created by mark on 15/9/13.
 */
public interface TokenService {
    String createToken();

    int persistence(Long id, String token);

    Token getToken(String value);
}
