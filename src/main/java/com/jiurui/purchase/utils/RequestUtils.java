package com.jiurui.purchase.utils;

import javax.servlet.http.HttpServletRequest;

public final class RequestUtils {

    /**
     * 是否ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    /**
     * 获取客户端ip
     *
     * @param request
     * @return
     */
    public static String getUserIPAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * ==================================================================
     *
     * @return :@return:String
     * ===============================================================
     * @方法名:getUserAgent
     * @功能:获得浏览器信息
     * @作者:郭超毅
     */
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }


    /**
     * 验证地址是否为站内
     * @param request
     * @param url
     * @param baseUrl
     * @return
     */
    public static boolean isInternalUrl(HttpServletRequest request, String url, String baseUrl) {

        if (url.startsWith(request.getContextPath() + "/") || url.startsWith(baseUrl + "/") || url.equalsIgnoreCase(baseUrl)) {

            return true;
        }

        return false;
    }
}
