package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * @authot : lxj
 * @Date : 2021/3/12 17:13
 */
public interface UserDao {
    User login(@Param("act") String loginAct,@Param("pwd") String loginPwd);
}
