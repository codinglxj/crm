package com.bjpowernode.crm.settings.serice.impl;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.serice.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @authot : lxj
 * @Date : 2021/3/13 13:06
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        System.out.println("进入验证登录操作");
        User user = userDao.login(loginAct, loginPwd);

        if (user == null) {
            throw new LoginException("账号密码错误");
        }

        //如果程序能够执行到这里说明账号密码是正确的
        //需要继续向下验证其它3项信息
        //验证失效时间

        //获取用户失效时间
        String expireTime = user.getExpireTime();
        //从工具类中，获取当前系统时间
        String currentTime = DateTimeUtil.getSysTime();

        if (expireTime.compareTo(currentTime) < 0) {
            throw new LoginException("账号已失效");
        }
        //验证用户锁定状态
        String lockState = user.getLockState();
        if ("0".equals(lockState)) {
            throw new LoginException("用户被锁定");
        }
        //判断ip地址
        String allowIp = user.getAllowIps();
        if (!allowIp.contains(ip)) {
            throw new LoginException("ip地址受限");
        }
        return user;

    }
}
