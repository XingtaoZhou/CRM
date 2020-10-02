package com.zxt.settings.controller;


import com.zxt.settings.domain.Activity;
import com.zxt.settings.domain.Clue;
import com.zxt.settings.domain.User;
import com.zxt.settings.service.ClueService;
import com.zxt.settings.vo.PageVo;
import com.zxt.utils.DateTimeUtil;
import com.zxt.utils.UUIDUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Clue")
public class ClueController {

    @Resource
    private ClueService service;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){

        List<User> userList = service.getUserList();
        return userList;
    }

    @RequestMapping("/pageList.do")
    @ResponseBody
    public PageVo<Clue> pageList(HttpServletRequest request){

        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        String fullname = request.getParameter("fullname");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String phone = request.getParameter("phone");
        String mphone = request.getParameter("mphone");
        String source = request.getParameter("source");
        String state = request.getParameter("state");
        Integer pageNo = Integer.valueOf(pageNoStr);
        Integer pageSize = Integer.valueOf(pageSizeStr);
        Integer count = (pageNo-1)*pageSize;

        Map<String,Object> map = new HashMap<>();
        map.put("fullname",fullname);
        map.put("owner",owner);
        map.put("company",company);
        map.put("phone",phone);
        map.put("mphone",mphone);
        map.put("source",source);
        map.put("state",state);
        map.put("count",count);
        map.put("pageSize",pageSize);

        PageVo<Clue> vo = service.pageList(map);

        return vo;

    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Map<String,Object> save(Clue clue , HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        clue.setCreateBy(user.getName());

        return service.save(clue);
    }

    @RequestMapping("/detail.do")
    public ModelAndView detail(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        String id = request.getParameter("id");
        Clue clue = service.detail(id);
        mv.addObject("clue",clue);
        mv.setViewName("/workbench/clue/detail.jsp");

        return mv;
    }

    @RequestMapping("/getActivityListByClueId.do")
    @ResponseBody
    public List<Activity> getActivityListByClueId(HttpServletRequest request){

        String id = request.getParameter("id");
        List<Activity> activityList = service.getActivityListByClueId(id);
        return activityList;
    }

    @RequestMapping("/deleteCARById.do")
    @ResponseBody
    public Map<String,Object> deleteCARById(HttpServletRequest request){

        String id = request.getParameter("id");
        return service.deleteCARById(id);

    }

    @RequestMapping("/getActivityByName.do")
    @ResponseBody
    public List<Activity> getActivityByName(HttpServletRequest request){

        String name = request.getParameter("name");
        String clueId = request.getParameter("clueId");

        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("clueId",clueId);
        return service.getActivityByName(map);
    }
}










