package com.bjpowernode.crm.interceptor;

import com.bjpowernode.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @authot : lxj
 * @Date : 2021/3/13 15:28
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //放行不应该拦截的资源
        System.out.println("进入拦截器，进行放行验证");
        String path = request.getServletPath();

        System.out.println("path:=====" + path);



        User user = (User) request.getSession().getAttribute("user");
        //如果user不为空，说明登录过
        if(user != null){
            return true;
        }else{
            //重定向到登录页
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            /*request.getRequestDispatcher("/login.jsp").forward(request,response);*/
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
