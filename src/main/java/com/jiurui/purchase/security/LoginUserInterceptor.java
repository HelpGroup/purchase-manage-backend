package com.jiurui.purchase.security;


import com.jiurui.purchase.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * 用户拦截
 */
public class LoginUserInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        LoginUser principal = (LoginUser) session.getAttribute(LoginUser.PRINCIPAL_ATTRIBUTE_NAME);
        if (null != principal) {
            return true;
        }

        response.addHeader("loginStatus", "accessDenied");
        response.sendError(403);
        return false;
    }

}
