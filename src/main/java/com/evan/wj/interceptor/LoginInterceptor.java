package com.evan.wj.interceptor;

import com.evan.wj.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor  implements HandlerInterceptor{
    /**
     * 看起来似乎比较长，其实就是判断 session 中是否存在 user 属性，
     * 如果存在就放行，如果不存在就跳转到 login 页面。
     * 这里使用了一个路径列表（requireAuthPages），可以在里面写下需要拦截的路径。
     * 当然我们也可以拦截所有路径，那样就不用写这么多了，但会有逻辑上的问题，就是你访问了 \login 页面，仍然会需要跳转，这样就会引发多次重定向问题。
     */

    @Override
    public boolean preHandle (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        HttpSession session = httpServletRequest.getSession();
//        String contextPath=session.getServletContext().getContextPath();
//        String[] requireAuthPages = new String[]{
//                "index",
//        };
//
//        String uri = httpServletRequest.getRequestURI();
//
//        uri = StringUtils.remove(uri, contextPath+"/");
//        String page = uri;
//
//        if(begingWith(page, requireAuthPages)){
//            User user = (User) session.getAttribute("user");
//            if(user==null) {
//                httpServletResponse.sendRedirect("login");
//                return false;
//            }
//        }
//        return true;


        // 放行 options 请求，否则无法让前端带上自定义的 header 信息，导致 sessionID 改变，shiro 验证失败
        if (HttpMethod.OPTIONS.toString().equals(httpServletRequest.getMethod())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }

        Subject subject = SecurityUtils.getSubject();
        // 使用 shiro 验证
        if (!subject.isAuthenticated() && !subject.isRemembered() ) {
            return false;
        }
        return true;
    }

    private boolean begingWith(String page, String[] requiredAuthPages) {
        boolean result = false;
        for (String requiredAuthPage : requiredAuthPages) {
            if(StringUtils.startsWith(page, requiredAuthPage)) {
                result = true;
                break;
            }
        }
        return result;
    }
}

