package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.exception.ActivityException;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

/**
 * @authot : lxj
 * @Date : 2021/3/15 16:56
 */
public interface ActivityService {

    Map<String, Object> getUserListAndActivity(String id);


    boolean save(Activity activity) throws ActivityException;
    PaginationVO<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    boolean update(Activity activity) throws ActivityException;

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAId(String activityID);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark ar);

    boolean updateRemark(ActivityRemark ar);
}
