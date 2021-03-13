package com.bjpowernode.crm.filter;

import com.bjpowernode.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @authot : lxj
 * @Date : 2021/3/13 16:29
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("进入到了servlet原生的过滤器中了，此过滤器只过滤.jsp的请求，动态请求已经由spingmvc中的拦截器拦截");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String path = request.getServletPath();
        System.out.println("过滤器中的filter，path:" + path);
                          //过滤器中的filter，path:/login.jsp

        //对/login.jsp进行放行
        if("/login.jsp".equals(path)){
            System.out.println("我已经放行了");
            chain.doFilter(req, resp);
        }else{

            User user = (User) request.getSession().getAttribute("user");

            //注意此处只拦截.jsp
            if(user != null){
                chain.doFilter(req, resp);
            }else{
                //重定向到登录界面
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }


    }

    @Override
    public void destroy() {

    }


}
