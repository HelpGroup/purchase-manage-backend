package com.jiurui.purchase.security;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 登陆用户信息
 */
public class LoginUser implements Serializable {

    public static final String PRINCIPAL_ATTRIBUTE_NAME = "USER_SESSION";

    private static final long serialVersionUID = -7057625355381670363L;

    private Long userId;

    private String account;

    private String name;

    private boolean isAdmin;

    /**
     * 获取当前登录用户
     * @return 当前登录用户
     */
    public static LoginUser getCurrUser() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
            LoginUser userPrincipal = (LoginUser) httpServletRequest.getSession().getAttribute(LoginUser.PRINCIPAL_ATTRIBUTE_NAME);
            return userPrincipal;
        }

        return null;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}
