package com.bjpowernode.crm.settings.serice;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.settings.domain.User;

/**
 * @authot : lxj
 * @Date : 2021/3/12 17:18
 */
public interface UserService {

    User login(String loginAct, String loginPwd, String ip) throws LoginException;
}

