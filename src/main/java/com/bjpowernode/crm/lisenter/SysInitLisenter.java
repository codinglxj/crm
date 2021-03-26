package com.bjpowernode.crm.lisenter;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @authot : lxj
 * @Date : 2021/3/19 9:30
 */
public class SysInitLisenter implements ServletContextListener {
    /**
     * 该方法是用来监听上下文域对象的方法,当服务器启动,上下文域对象创建
     * 对象创建完毕后,马上执行该方法
     *
     * event: 该参数能够取得监听的对象
     *      监听的是什么对象,就可以通过该参数取得什么对象
     *      例如我们现在监听的是上下文域对象,通过该参数就可以取得上下文域对象
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("start initServlet");
        System.out.println("服务器缓存处理数据字典开始");
        ServletContext servletContext = servletContextEvent.getServletContext();
        DicService dicService = WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(DicService.class);

        /**
         * 应该管业务层要什么
         *  7个List
         *
         *  可以打包称为一个map
         *  业务层应该是这样来保存数据的:
         *      map.put("appellationList",dvList1);
         *      map.put("clueStateList",dvList2);
         *      map.put("stageList",dvList3);
         */
        Map<String, List<DicValue>> map = new HashMap<String, List<DicValue>>();
        map = dicService.getAll(servletContext);


        Set<String> keySet = map.keySet();
        for(String key: keySet){
            servletContext.setAttribute(key,map.get(key));
        }
        System.out.println("服务器缓存处理数据字典结束");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
