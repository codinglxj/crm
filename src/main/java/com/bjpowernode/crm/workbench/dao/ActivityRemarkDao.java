package com.bjpowernode.crm.workbench.dao;

/**
 * @authot : lxj
 * @Date : 2021/3/17 12:13
 */
public interface ActivityRemarkDao {
    int getCountByIds(String[] ids);

    int deleteByIds(String[] ids);
}
