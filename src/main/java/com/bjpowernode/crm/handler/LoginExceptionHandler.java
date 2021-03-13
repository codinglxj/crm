package com.bjpowernode.crm.handler;

import com.bjpowernode.crm.exception.LoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @authot : lxj
 * @Date : 2021/3/13 13:18
 */
@ControllerAdvice
public class LoginExceptionHandler {
    @ExceptionHandler(value = LoginException.class)
    @ResponseBody
    public Map<String, Object> login(Exception e){

        System.out.println("进入全局异常处理类");
        String msg = e.getMessage();

        System.out.println(msg);

        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("msg", msg);

        return map;
    }
}
