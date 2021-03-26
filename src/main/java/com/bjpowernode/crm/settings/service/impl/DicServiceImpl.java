package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.DicTypeDao;
import com.bjpowernode.crm.settings.dao.DicValueDao;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @authot : lxj
 * @Date : 2021/3/18 18:51
 */
@Service
public class DicServiceImpl implements DicService {

    @Override
    public Map<String, List<DicValue>> getAll(ServletContext servletContext) {
        DicTypeDao dicTypeDao = WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(DicTypeDao.class);
        DicValueDao dicValueDao = WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(DicValueDao.class);

        List<String> listType = dicTypeDao.getType();
        List<DicValue> listValue = new ArrayList<>();

        Map<String,List<DicValue>> map = new HashMap<>();
        for(String type: listType){
            listValue = dicValueDao.getAllValue(type);
            map.put(type,listValue);

        }


        return map;
    }
}
