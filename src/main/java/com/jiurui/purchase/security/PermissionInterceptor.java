package com.jiurui.purchase.security;

import com.jiurui.purchase.model.Role;
import com.jiurui.purchase.model.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mark on 15/9/14.
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User current = (User) request.getSession().getAttribute("user");
        if(!current.getRoleId().equals(Role.BRANCH)) return true;
        else {
            response.addHeader("accessDenied", "NO PERMISSION");
            response.sendError(403);
            return false;
        }
    }

}
