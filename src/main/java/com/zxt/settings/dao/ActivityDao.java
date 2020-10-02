package com.zxt.settings.dao;

import com.zxt.settings.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {

    int saveActivity(Activity activity);

    List<Activity> pageList(Map<String, Object> pageMap);

    Integer countPageList(Map<String, Object> pageMap);

    int deleteActivity(String[] ids);

    Activity selectById(String id);

    int updateActivity(Activity activity);

    Activity detail(String id);

    List<Activity> getActivityByName(Map<String,Object> map);
}
