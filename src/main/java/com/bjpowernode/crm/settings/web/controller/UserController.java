package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.serice.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @authot : lxj
 * @Date : 2021/3/13 11:39
 */
@Controller
@RequestMapping("/settings/user")
public class UserController {

    @Resource
    UserService userService;

    @RequestMapping(value = "/login.do" )
    @ResponseBody
    public Map<String, Object> userLogin(HttpServletRequest request, String loginAct, String loginPwd) throws LoginException {

        System.out.println("进入user登录的controller 层");

        //将密码用md5加密
        loginPwd = DigestUtils.md5DigestAsHex(loginPwd.getBytes());

        //获取用户的ip地址
        String ip = request.getRemoteAddr();
        System.out.println(ip);

        User user = userService.login(loginAct, loginPwd,ip);
        System.out.println(user.getName());
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        request.getSession().setAttribute("user",user);

        return map;

    }

}
