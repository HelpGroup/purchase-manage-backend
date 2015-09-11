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

    /**
     * URL编码
     */
    private String urlEscapingCharset = "UTF-8";

    /**
     * 登录页面地址
     */
    private String loginUrl = "/login";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        LoginUser principal = (LoginUser) session.getAttribute(LoginUser.PRINCIPAL_ATTRIBUTE_NAME);
        if (null != principal) {
            return true;
        }

        if (RequestUtils.isAjaxRequest(request)) {
            response.addHeader("loginStatus", "accessDenied");
            response.sendError(403);
            return false;
        }

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            String queryString = null != request.getQueryString() ? request.getRequestURI() + "?" + request.getQueryString() : request.getRequestURI();
            response.sendRedirect(request.getContextPath() + this.loginUrl + "?returnUrl=" + URLEncoder.encode(queryString, this.urlEscapingCharset));
        } else {
            response.sendRedirect(request.getContextPath() + this.loginUrl);
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            String str = modelAndView.getViewName();
            if (!StringUtils.startsWith(str, "redirect:")) {
                modelAndView.addObject("member", LoginUser.getCurrUser());
            }
        }

    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
