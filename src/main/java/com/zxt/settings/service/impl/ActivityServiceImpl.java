package com.zxt.settings.service.impl;

import com.zxt.settings.dao.UserDao;
import com.zxt.settings.domain.User;
import com.zxt.settings.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private UserDao userDao;

    @Override
    public List<User> getUserList() {

        return userDao.getUserList();
    }
}
