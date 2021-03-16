package com.bjpowernode.crm.handler;

import com.bjpowernode.crm.exception.ActivityException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @authot : lxj
 * @Date : 2021/3/15 19:33
 */
@ControllerAdvice
public class ActivityExceptionHandler {
    @ExceptionHandler(value = ActivityException.class)
    public Map<String, Object> activityException(Exception e) {
        String msg = e.getMessage();
        System.out.println(msg);
        Map<String, Object> map = new HashMap<>();
        //执行到此处说明出现抛出了异常
        map.put("success", false);
        return map;

    }

}
