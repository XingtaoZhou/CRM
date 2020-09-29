package com.zxt.settings.controller;


import com.zxt.settings.dao.UserDao;
import com.zxt.settings.domain.Activity;
import com.zxt.settings.domain.User;
import com.zxt.settings.service.ActivityService;
import com.zxt.settings.vo.PageVo;
import com.zxt.utils.DateTimeUtil;
import com.zxt.utils.UUIDUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ActivityController {

    @Resource
    private ActivityService service;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){
        List<User> userList = service.getUserList();
        return userList;
    }

    @RequestMapping("/saveActivity.do")
    @ResponseBody
    public Map<String,Object> saveActivity(Activity activity , HttpServletRequest request){

        Map<String,Object> map = new HashMap<>();

        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        activity.setCreateBy(((User)request.getSession().getAttribute("user")).getName());

        int result = service.saveActivity(activity);

        if (result <= 0){
            //插入失败
            map.put("success",false);
        }else {
            //插入成功
            map.put("success",true);
        }
        return map;
    }
    @RequestMapping("/pageList.do")
    @ResponseBody
    public PageVo<Activity> pageList(HttpServletRequest request){

        String pageNostr = request.getParameter("pageNo");
        String pageSizestr = request.getParameter("pageSize");
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        Integer pageNo = Integer.valueOf(pageNostr);
        Integer pageSize = Integer.valueOf(pageSizestr);
        Integer count = (pageNo-1)*pageSize;

        Map<String,Object> pageMap = new HashMap<>();
        pageMap.put("name",name);
        pageMap.put("owner",owner);
        pageMap.put("startDate",startDate);
        pageMap.put("endDate",endDate);
        pageMap.put("pageSize",pageSize);
        pageMap.put("count",count);

        //创建一个vo来接收pageList的返回值，因为这个方法在多个模块都会用到，所有用vo不用map
        PageVo<Activity> vo = service.pageList(pageMap);

        return vo;
    }
}
