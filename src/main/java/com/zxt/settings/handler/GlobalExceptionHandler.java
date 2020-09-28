package com.zxt.settings.handler;

import com.zxt.settings.exception.ActLockException;
import com.zxt.settings.exception.IpException;
import com.zxt.settings.exception.NamePwdException;
import com.zxt.settings.exception.TimeoutException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/*@ControllerAdvice
public class GlobalExceptionHandler {*/


    /*@ExceptionHandler(value = NamePwdException.class)
    @ResponseBody
    public Map<String,Object> doNamePwdException(Exception e){
        Map<String,Object> map = new HashMap<>();
        String msg = e.getMessage();
        map.put("success",false);
        map.put("msg",msg);
        return map;
    }
    @ExceptionHandler(value = TimeoutException.class)
    @ResponseBody
    public Map<String,Object> doTimeoutException(Exception e){
        Map<String,Object> map = new HashMap<>();
        String msg = e.getMessage();
        map.put("success",false);
        map.put("msg",msg);
        return map;
    }
    @ExceptionHandler(value = ActLockException.class)
    @ResponseBody
    public Map<String,Object> doActLockException(Exception e){
        Map<String,Object> map = new HashMap<>();
        String msg = e.getMessage();
        map.put("success",false);
        map.put("msg",msg);
        return map;
    }
    @ExceptionHandler(value = IpException.class)
    @ResponseBody
    public Map<String,Object> doIpException(Exception e){
        Map<String,Object> map = new HashMap<>();
        String msg = e.getMessage();
        map.put("success",false);
        map.put("msg",msg);
        return map;
    }
}*/
