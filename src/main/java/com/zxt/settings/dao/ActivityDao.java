package com.zxt.settings.dao;

import com.zxt.settings.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {

    int saveActivity(Activity activity);

    List<Activity> pageList(Map<String, Object> pageMap);

    Integer countPageList(Map<String, Object> pageMap);
}
