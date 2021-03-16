package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.exception.ActivityException;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @authot : lxj
 * @Date : 2021/3/15 17:01
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityDao activityDao;


    //
    @Override
    public boolean save(Activity activity) throws ActivityException {
        int count = activityDao.save(activity);
        if(count != 1){
            throw new ActivityException("市场活动添加失败");
        }
        return true;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {
        //计算出total的数量
        Integer total = activityDao.getTotalByCondition(map);
        System.out.println("total查询到的数据的总量:" + total);
        //取得dataList
        List<Activity> dataList = activityDao.getActivityListByCondition(map);

        //分装为PaginationVO,并返回。
        PaginationVO<Activity> paginationVo = new PaginationVO<>();
        paginationVo.setTotal(total);
        paginationVo.setDataList(dataList);
        return paginationVo;
    }
}
