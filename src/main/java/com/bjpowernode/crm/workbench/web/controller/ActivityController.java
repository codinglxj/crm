package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.exception.ActivityException;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @authot : lxj
 * @Date : 2021/3/15 16:54
 */
@Controller
@RequestMapping("/workbench/activity/")
public class ActivityController {
    @Resource
    private UserService userService;

    @Resource
    private ActivityService activityService;

    @RequestMapping(value = "/getUserList.do")
    @ResponseBody
    public List<User> getUserList() {

        System.out.println("进入市场活动控制器");
        List<User> list = userService.getUserList();
        return list;

    }

    @RequestMapping(value = "/save.do")
    @ResponseBody
    public Map<String, Object> doSave(HttpServletRequest request, Activity activity) throws ActivityException {

        System.out.println("执行市场活动添加操作");
        //使用UUID创建主键
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        activity.setId(id);
        //创建当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        activity.setCreateTime(createTime);
        //创建人：当前登录用户
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        activity.setCreateBy(createBy);

        //调用ActivityService中的业务方法
        boolean flag = activityService.save(activity);

        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        return map;
    }

    @RequestMapping(value = "/pageList.do")
    @ResponseBody
    public PaginationVO<Activity> pageList(HttpServletRequest request) {
        System.out.println("进入到市场活动信息列表的操作(结合条件查询+分页查询)");
        String pageNoStr = request.getParameter("pageNo");
        Integer pageNo = Integer.valueOf(pageNoStr);

        String pageSizeStr = request.getParameter("pageSize");

        //每页展示的个数
        Integer pageSize = Integer.valueOf(pageSizeStr);

        //计算出要略过的记录数
        Integer skipCount = (pageNo -1) * pageSize;
        String name = request.getParameter("search-name");
        String owner = request.getParameter("search-owner");
        String startDate = request.getParameter("search-startDate");
        String endDate = request.getParameter("search-endDate");


        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);

        PaginationVO<Activity> paginationVO = activityService.pageList(map);
        return paginationVO;


    }


}
