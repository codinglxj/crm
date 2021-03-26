package com.bjpowernode.crm.settings.dao;

import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;

/**
 * @authot : lxj
 * @Date : 2021/3/18 18:52
 */
public interface DicValueDao {
    List<DicValue> getAllValue(String type);
}
