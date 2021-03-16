package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.exception.ActivityException;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.Map;

/**
 * @authot : lxj
 * @Date : 2021/3/15 16:56
 */
public interface ActivityService {

    boolean save(Activity activity) throws ActivityException;
    PaginationVO<Activity> pageList(Map<String, Object> map);
}
