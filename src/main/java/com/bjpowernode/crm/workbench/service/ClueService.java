package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

/**
 * @authot : lxj
 * @Date : 2021/3/18 17:48
 */
public interface ClueService {
    List<User> getUserList();

    boolean doSave(Clue clue);


    Clue doDetail(String id);

    List<Activity> getActivityListById(String id);

    boolean unBund(String id);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);


    boolean doBund(String clueId, String[] xzStr);

    List<Activity> getActivityListByName(String aname);

    boolean doConvert(String clueId, Tran tran, String createBy);
}
