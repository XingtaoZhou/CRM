package com.zxt.settings.controller;


import com.zxt.settings.domain.Tran;
import com.zxt.settings.domain.TranHistory;
import com.zxt.settings.domain.User;
import com.zxt.settings.service.TransactionService;
import com.zxt.utils.DateTimeUtil;
import com.zxt.utils.UUIDUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/save.do")
    public ModelAndView save(Tran tran,HttpServletRequest request){

        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute("user");
        String createBy = user.getName();
        String createTime = DateTimeUtil.getSysTime();
        String customerName = request.getParameter("customerName");
        tran.setId(UUIDUtil.getUUID());
        tran.setCreateBy(createBy);
        tran.setCreateTime(createTime);

        Boolean flag = service.save(tran,customerName);
        //重定向
        mv.setViewName("redirect:/workbench/transaction/index.jsp");
        return mv;
    }

    @RequestMapping("/detail.do")
    public ModelAndView detail(HttpServletRequest request){

        ModelAndView mv = new ModelAndView();
        String id = request.getParameter("id");

        //根据id找到tran,多表联查
        Tran tran = service.detail(id);
        //可能性
        ServletContext application = request.getServletContext();
        Map<String,String> pMap = (Map<String, String>) application.getAttribute("pMap");

        String possibility = pMap.get(tran.getStage());
        mv.addObject("possibility",possibility);
        mv.addObject("tran",tran);
        mv.setViewName("/workbench/transaction/detail.jsp");
        return mv;
    }

    @RequestMapping("/getHistoryListByTranId.do")
    @ResponseBody
    public List<TranHistory> getHistoryListByTranId(HttpServletRequest request){
        String id = request.getParameter("id");
        return service.getHistoryListByTranId(id);
    }



}















