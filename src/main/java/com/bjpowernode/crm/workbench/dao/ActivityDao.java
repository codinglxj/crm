package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * @authot : lxj
 * @Date : 2021/3/15 16:58
 */
public interface ActivityDao {

    //添加市场活动
    int save(Activity activity);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    Integer getTotalByCondition(Map<String, Object> map);

    int deleteByIds(String[] ids);

    Activity getActivityById(String id);

    int update(Activity activity);

    Activity detail(String id);

    List<Activity> getActivityListById(String id);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

    List<Activity> getActivityListByName(String aname);
}
