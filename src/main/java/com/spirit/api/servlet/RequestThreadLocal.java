package com.spirit.api.servlet;

import com.spirit.commons.common.ApiLogger;
import com.spirit.commons.common.trace.RequestContext;

import javax.servlet.http.HttpServletRequest;

public class RequestThreadLocal implements SpiritServletFilter {

    private static final ThreadLocal<HttpServletRequest> REQUEST_THREAD_LOCAL = new ThreadLocal<HttpServletRequest>();

    public static void setRequest(HttpServletRequest request) {
        REQUEST_THREAD_LOCAL.set(request);
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    @Override
    public void before(HttpServletRequest request){
        RequestContext.init();
        RequestThreadLocal.setRequest(request);
        RequestContext requestContext = RequestContext.get();
    }

    @Override
    public void after(HttpServletRequest request){
        RequestThreadLocal.setRequest(null);
        RequestContext.finish();
    }
}
