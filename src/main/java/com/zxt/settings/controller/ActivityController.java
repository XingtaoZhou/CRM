package com.zxt.settings.controller;


import com.zxt.settings.dao.UserDao;
import com.zxt.settings.domain.User;
import com.zxt.settings.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

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
}
