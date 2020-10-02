package com.zxt.settings.service.impl;

import com.zxt.settings.dao.ActivityDao;
import com.zxt.settings.dao.ClueActivityRelationDao;
import com.zxt.settings.dao.ClueDao;
import com.zxt.settings.dao.UserDao;
import com.zxt.settings.domain.Activity;
import com.zxt.settings.domain.Clue;
import com.zxt.settings.domain.User;
import com.zxt.settings.service.ClueService;
import com.zxt.settings.vo.PageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ClueServiceImpl implements ClueService {

    @Resource
    private UserDao userDao;

    @Resource
    private ClueDao clueDao;

    @Resource
    private ClueActivityRelationDao clueActivityRelationDao;

    @Resource
    private ActivityDao activityDao;

    @Override
    public List<User> getUserList() {

        List<User> userList = userDao.getUserList();

        return userList;
    }

    @Override
    public Map<String, Object> save(Clue clue) {

        Boolean flag = true;

        int result = clueDao.save(clue);
        if (result != 1){
            flag = false;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        return map;
    }

    @Override
    public PageVo<Clue> pageList(Map<String, Object> map) {

        //获得total
        int total = clueDao.countPageList(map);

        //获得clueList
        List<Clue> clueList = clueDao.pageList(map);

        PageVo<Clue> vo = new PageVo<>();
        vo.setDataList(clueList);
        vo.setTotal(total);

        return vo;
    }

    @Override
    public Clue detail(String id) {

        Clue clue = clueDao.detail(id);
        return clue;
    }

    @Override
    public List<Activity> getActivityListByClueId(String id) {

        List<Activity> activityList = clueDao.getActivityListByClueId(id);

        return activityList;
    }

    @Override
    public Map<String, Object> deleteCARById(String id) {

        Boolean flag = true;

        int result = clueActivityRelationDao.deleteCARById(id);
        if (result != 1){
            flag = false;
        }

        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        return map;
    }

    @Override
    public List<Activity> getActivityByName(Map<String,Object> map) {

        List<Activity> activityList = activityDao.getActivityByName(map);

        return activityList;
    }
}










