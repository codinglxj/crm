package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicValue;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

/**
 * @authot : lxj
 * @Date : 2021/3/18 18:51
 */
public interface DicService {
    Map<String, List<DicValue>> getAll(ServletContext servletContext);
}
