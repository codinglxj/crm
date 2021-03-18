package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.exception.ActivityException;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Controller;
import org.springframework.util.unit.DataUnit;
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
        Integer skipCount = (pageNo - 1) * pageSize;
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

    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public Map<String, Object> delete(HttpServletRequest request) {

        System.out.println("进去删除市场活动的控制器方法中====");

        String[] ids = request.getParameterValues("id");
        boolean flag = activityService.delete(ids);
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);

        return map;

    }

    @RequestMapping(value = "/getUserListAndActivity.do")
    @ResponseBody
    public Map<String, Object> getUserListAndActivity(String id){
        System.out.println("id的值：" + id);
        System.out.println("进入修改市场活动");
        Map<String, Object> map = activityService.getUserListAndActivity(id);


        return map;

    }

    @RequestMapping(value = "/update.do")
    @ResponseBody
    public Map<String, Object> doUpdate(HttpServletRequest request, Activity activity) throws ActivityException {

        System.out.println("执行市场活动修改操作");

        //创建当前系统时间
        String editTime = DateTimeUtil.getSysTime();
        activity.setEditTime(editTime);

        //创建人：当前登录用户
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        activity.setEditBy(editBy);
        //调用ActivityService中的业务方法
        boolean flag = activityService.update(activity);

        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        return map;
    }


    //详细展示市场活动
    @RequestMapping(value = "/detail.do")
    public String detail(HttpServletRequest request){
        String id = request.getParameter("id");
        Activity a = activityService.detail(id);
        request.setAttribute("a", a);

        return "forward:/workbench/activity/detail.jsp";
    }

    //展示与市场活动有关的备注(remark)
    @RequestMapping(value = "/getRemarkListByAId.do")
    @ResponseBody
    public List<ActivityRemark> getRemarkListByAid(String activityID){
        List<ActivityRemark> list = activityService.getRemarkListByAId(activityID);
        return list;
    }

    //删除市场活动备注信息
    @RequestMapping(value = "/deleteRemark.do")
    @ResponseBody
    public Map<String, Object> deleteRemark(String id){
        boolean flag = activityService.deleteRemark(id);

        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        return map;
    }

    //添加新的备注信息
    @RequestMapping(value = "/saveRemarkBtn.do")
    @ResponseBody
    public Map<String, Object> saveRemark(HttpServletRequest request, String activityId, String noteContent){
        ActivityRemark ar = new ActivityRemark();
        //使用uuid生成主键
        String id = UUIDUtil.getUUID();
        //创建时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人
        String createBy = ((User)(request.getSession().getAttribute("user"))).getName();

        //
        String editFlag="0";
        ar.setId(id);
        ar.setActivityId(activityId);
        ar.setNoteContent(noteContent);
        ar.setCreateTime(createTime);
        ar.setCreateBy(createBy);
        ar.setEditFlag(editFlag);
        boolean flag = activityService.saveRemark(ar);
        Map<String,Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("ar", ar);
        return map;
    }

    //修改备注并保存
    @RequestMapping(value = "/updateRemark.do")
    @ResponseBody
    public Map<String, Object> updateRemark(HttpServletRequest request, String id ,String noteContent){
        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)(request.getSession().getAttribute("user"))).getName();
        String editFlag = "1";
        ar.setEditFlag(editFlag);
        ar.setEditTime(editTime);
        ar.setEditBy(editBy);

        boolean flag = activityService.updateRemark(ar);
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("ar", ar);
        return map;


    }


}
