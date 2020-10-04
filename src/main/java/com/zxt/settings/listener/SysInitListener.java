package com.zxt.settings.listener;

import com.zxt.settings.domain.DicValue;
import com.zxt.settings.service.DicService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;


/*
*   监听类中不能使用注解自动注入
*   spring的注入是在filter和listener之后的，（顺序是这样的listener >>  filter >> servlet >>  spring ）
*   因此要使用ClassPathXmlApplicationContext 来获得service
*
* */
@Component
public class SysInitListener implements ServletContextListener {

    private DicService dicservice;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("conf/applicationContext.xml");
        dicservice = (DicService) ctx.getBean("dicservice");

        System.out.println("数据字典处理开始");
        ServletContext application = sce.getServletContext();

        Map<String, List<DicValue>> map = dicservice.getDic();

        Set<String> set = map.keySet();

        for (String key:set){

            application.setAttribute(key,map.get(key));

        }
        System.out.println("数据字典处理结束");

        //解析properties文件
        System.out.println("处理properties文件开始");
        Map<String,String> pMap = new HashMap<>();
        ResourceBundle rb = ResourceBundle.getBundle("properties/Stage2Possibility");
        Enumeration<String> e = rb.getKeys();
        while (e.hasMoreElements()){
            String key = e.nextElement();
            String value = rb.getString(key);
            pMap.put(key,value);
        }
        application.setAttribute("pMap",pMap);
        System.out.println("处理properties文件结束");

    }
}












