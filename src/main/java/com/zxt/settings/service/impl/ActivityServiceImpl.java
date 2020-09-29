package com.zxt.settings.service.impl;

import com.zxt.settings.dao.ActivityDao;
import com.zxt.settings.dao.UserDao;
import com.zxt.settings.domain.Activity;
import com.zxt.settings.domain.User;
import com.zxt.settings.service.ActivityService;
import com.zxt.settings.vo.PageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private UserDao userDao;

    @Resource
    private ActivityDao activityDao;

    @Override
    public List<User> getUserList() {

        return userDao.getUserList();
    }

    @Override
    public int saveActivity(Activity activity) {

        return activityDao.saveActivity(activity);
    }

    @Override
    public PageVo<Activity> pageList(Map<String, Object> pageMap) {

        PageVo<Activity> vo = new PageVo<>();
        List<Activity> activityList = activityDao.pageList(pageMap);
        Integer total = activityDao.countPageList(pageMap);
        System.out.println("size========="+activityList.size());
        vo.setDataList(activityList);
        vo.setTotal(total);

        return vo;
    }
}
