package com.zxt.settings.service.impl;

import com.zxt.settings.dao.ActivityDao;
import com.zxt.settings.dao.ActivityRemarkDao;
import com.zxt.settings.dao.UserDao;
import com.zxt.settings.domain.Activity;
import com.zxt.settings.domain.ActivityRemark;
import com.zxt.settings.domain.User;
import com.zxt.settings.service.ActivityService;
import com.zxt.settings.vo.PageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private UserDao userDao;

    @Resource
    private ActivityDao activityDao;

    @Resource
    private ActivityRemarkDao remarkDao;

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
        //System.out.println("size========="+activityList.size());
        vo.setDataList(activityList);
        vo.setTotal(total);

        return vo;
    }

    @Override
    public Boolean deleteActivity(String[] ids) {

        Boolean flag = true;
        //查询受影响的Remark表中数据的条数
        int count1 = remarkDao.getCountByIds(ids);
        //删除的Remark中的数据条数
        int count2 = remarkDao.deleteByIds(ids);
        if (count1 != count2){
            flag = false;
        }

        //删除的Activity中的数据条数
        int result = activityDao.deleteActivity(ids);

        if (result != ids.length){
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {

        //获得userList
        List<User> userList = userDao.getUserList();
        //根据id查activity
        Activity activity = activityDao.selectById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("userList",userList);
        map.put("activity",activity);

        return map;
    }

    @Override
    public Map<String, Object> updateActivity(Activity activity) {

        Boolean flag = true;
        int result = activityDao.updateActivity(activity);
        //System.out.println("result======"+result);
        if (result != 1){
            flag = false;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success",flag);
        return map;
    }

    @Override
    public Activity detail(String id) {

        Activity activity = activityDao.detail(id);

        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkById(String id) {
        List<ActivityRemark> list = remarkDao.getRemarkById(id);
        return list;
    }

    @Override
    public Map<String,Object> deleteRemark(String id) {

        Boolean flag = true;
        int result = remarkDao.deleteRemark(id);
        if (result != 1){
            flag = false;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        return map;
    }
}
