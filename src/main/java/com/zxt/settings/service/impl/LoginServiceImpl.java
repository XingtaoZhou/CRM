package com.zxt.settings.service.impl;

import com.zxt.settings.dao.UserDao;
import com.zxt.settings.domain.User;
import com.zxt.settings.exception.*;
import com.zxt.settings.service.LoginService;
import com.zxt.utils.DateTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserDao userDao;


    @Override
    public User login(String loginAct, String loginPwd, String ip) throws MyException {
        Map<String,Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user = userDao.selectUser(map);

        if (user == null){
            throw new NamePwdException("账号密码不正确");
        }

        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(currentTime) < 0){
            throw new TimeoutException("账号已失效");
        }

        String lockState = user.getLockState();
        if ("0".equals(lockState)){
            throw new ActLockException("账号已被锁定");
        }

        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)){
            throw new IpException("ip地址受限");
        }
        return user;
    }
}

