package com.zxt.settings.controller;


import com.zxt.settings.domain.User;
import com.zxt.settings.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/Transaction")
public class TransactionController {

    @Resource
    private TransactionService service;

    @RequestMapping("/add.do")
    public ModelAndView add(){
        ModelAndView mv = new ModelAndView();
        List<User> userList = service.getUserList();
        mv.addObject("userList",userList);
        mv.setViewName("/workbench/transaction/save.jsp");
        return mv;
    }

    @RequestMapping("/getCustomerName.do")
    @ResponseBody
    public List<String> getCustomerName(HttpServletRequest request){
        String name = request.getParameter("name");
        return service.getCustomerName(name);
    }

}
