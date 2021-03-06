package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @authot : lxj
 * @Date : 2021/3/18 17:53
 */
@Controller
@RequestMapping(value = "/workbench/clue/")
public class ClueController {
    @Resource
    private ClueService clueService;


    @RequestMapping(value = "/getUserList.do")
    @ResponseBody
    public List<User> getUserList() {
        List<User> list = new ArrayList<>();
        list = clueService.getUserList();
        return list;
    }

    @RequestMapping(value = "/save.do")
    @ResponseBody
    public Map<String, Object> doSave(HttpServletRequest request, Clue clue) {

        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        clue.setCreateTime(createTime);
        clue.setId(id);
        clue.setCreateBy(createBy);

        boolean flag = clueService.doSave(clue);
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        return map;
    }

    @RequestMapping(value = "/detail.do")
    @ResponseBody
    public ModelAndView doDetail(String id) {
        Clue clue = clueService.doDetail(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("clue", clue);
        modelAndView.setViewName("forward:/workbench/clue/detail.jsp");
        return modelAndView;
    }

    @RequestMapping(value = "/getActivityListById.do")
    @ResponseBody
    public List<Activity> getActivityListById(String id) {
        List<Activity> list = clueService.getActivityListById(id);
        return list;

    }

    @RequestMapping(value = "/unbund.do")
    @ResponseBody
    public Map<String, Object> unBund(String id) {

        boolean flag = clueService.unBund(id);
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        return map;

    }

    @RequestMapping(value = "/getActivityListByNameAndNotByClueId.do")
    @ResponseBody
    public List<Activity> getActivityListByNameAndNotByClueId(String aname, String clueId) {
        Map<String, String> map = new HashMap<>();
        map.put("aname", aname);
        map.put("clueId", clueId);
        List<Activity> list = clueService.getActivityListByNameAndNotByClueId(map);
        return list;
    }

    @RequestMapping(value = "/bund.do")
    @ResponseBody
    public Map<String, Object> doBund(HttpServletRequest request) {


        String clueId = request.getParameter("cid");
        String[] xzStr = request.getParameterValues("aid");

        boolean flag = clueService.doBund(clueId, xzStr);
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        return map;
    }

    @RequestMapping(value = "/getActivityListByName.do")
    @ResponseBody
    public List<Activity> getActivityListByName(String aname) {
        List<Activity> list = clueService.getActivityListByName(aname);
        return list;
    }

    @RequestMapping(value = "/convert.do")
    @ResponseBody
    public ModelAndView doConvert(HttpServletRequest request) {
        System.out.println("???????????????????????????");
        String clueId = request.getParameter("clueId");

        String flag = request.getParameter("flag");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        Tran tran = null;
        if ("a".equals(flag)) {

            //a??????,??????????????????????????????
            tran = new Tran();


            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String expectedDate = request.getParameter("expectedDate");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("activityId");
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();

            tran.setMoney(money);
            tran.setName(name);
            tran.setExpectedDate(expectedDate);
            tran.setStage(stage);
            tran.setActivityId(activityId);
            tran.setId(id);
            tran.setCreateTime(createTime);
            tran.setCreateBy(createBy);


        }

        /*
            1.???????????????????????????clueId,????????????clueId????????????????????????????????????
            2.?????????????????????tran,??????????????????????????????????????????????????????(tran???????????????)


        */
        boolean flag1 = clueService.doConvert(clueId, tran, createBy);

        ModelAndView mav = new ModelAndView();
        if (flag1) {
            mav.setViewName("redirect:/workbench/clue/index.jsp");
        } else {
            mav.setViewName("redirect:/workbench/clue/error.jsp");

        }
        return mav;

    }
}
