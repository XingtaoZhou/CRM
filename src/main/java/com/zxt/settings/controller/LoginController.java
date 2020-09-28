package com.zxt.settings.controller;

import com.zxt.settings.domain.User;
import com.zxt.settings.exception.MyException;
import com.zxt.settings.service.LoginService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Resource
    private LoginService service;

    @RequestMapping("/login.do")
    @ResponseBody
    public Map<String,Object> login(User user, HttpServletRequest request)  {

        String loginAct = user.getLoginAct();
        String loginPwd = user.getLoginPwd();
        //MD5加密
        loginPwd = DigestUtils.md5Hex(loginPwd);
        //接收ip地址
        String ip = request.getRemoteAddr();
        System.out.println("ip========="+ip);
        Map<String,Object> map = new HashMap<>();

        try {
            User returnUser = service.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user",returnUser);
            map.put("success",true);
        } catch (MyException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            map.put("success",false);
            map.put("msg",msg);
        }
        return map;
    }
}
