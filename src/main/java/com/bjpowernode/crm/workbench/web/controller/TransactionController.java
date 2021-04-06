package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @authot : lxj
 * @Date : 2021/3/29 10:19
 */
@Controller
@RequestMapping(value = "/workbench/transaction/")
public class TransactionController {
    @Resource
    private TransactionService transactionService;
    @Resource
    private CustomerService customerService;

    @RequestMapping(value = "/add.do")
    public ModelAndView doAdd() {

        //进入交易创建页面
        System.out.println("进入交易创建页面");

        List<User> userList = transactionService.getUserList();
        ModelAndView mav = new ModelAndView();
        mav.addObject("userList", userList);

        mav.setViewName("forward:/workbench/transaction/save.jsp");
        return mav;

    }
    @RequestMapping(value = "/getCustomerName.do")
    @ResponseBody
    public List<String> getCustomerName(String name){
        System.out.println("name:" + name);
        List<String> list = customerService.getCustomerName(name);
        return list;
    }

    @RequestMapping(value = "/save.do")
    @ResponseBody
    public ModelAndView doSave(HttpServletRequest request, Tran tran){

        String customerName = request.getParameter("customerName");

        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String id = UUIDUtil.getUUID();

        tran.setCreateBy(createBy);
        tran.setCreateTime(createTime);
        tran.setId(id);
        boolean flag = transactionService.doSave(tran, customerName);

        ModelAndView mav = new ModelAndView();

        //应该有一个错误界面，如果flag为false,则重定向到一个错误的页面。
        mav.setViewName("redirect:/workbench/transaction/index.jsp");
        return  mav;
    }


}
