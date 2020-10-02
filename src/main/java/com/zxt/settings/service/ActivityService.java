package com.zxt.settings.service;

import com.zxt.settings.domain.Activity;
import com.zxt.settings.domain.ActivityRemark;
import com.zxt.settings.domain.User;
import com.zxt.settings.vo.PageVo;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    List<User> getUserList();

    int saveActivity(Activity activity);

    PageVo<Activity> pageList(Map<String, Object> pageMap);

    Boolean deleteActivity(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    Map<String, Object> updateActivity(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkById(String id);

    Map<String,Object> deleteRemark(String id);

    Map<String, Object> saveRemark(ActivityRemark remark);

    Map<String, Object> updateRemark(ActivityRemark remark);
}
