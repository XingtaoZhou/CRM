package com.zxt.settings.handler;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    /*
    *用户登录验证
    *
    * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*Boolean flag = false;
        //获得请求地址
        String path = request.getRequestURI();
        //如果path中有login就放行
        if(path.contains("login")){
            flag = true;
        }
        if (!flag) {
            Object attr = request.getSession().getAttribute("user");
            if (attr == null) {
                //用户没有登录，拦截请求，重定向到登录页
                //request.getRequestDispatcher("/login.jsp").forward(request, response);
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                //return false;
            }else {
                flag = true;
            }
        }
        return flag;*/
        return true;

    }
}
