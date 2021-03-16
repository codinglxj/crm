package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.settings.domain.User;

import java.util.List;

/**
 * @authot : lxj
 * @Date : 2021/3/12 17:18
 */
public interface UserService {

     List<User> getUserList();
    User login(String loginAct, String loginPwd, String ip) throws LoginException;
}

