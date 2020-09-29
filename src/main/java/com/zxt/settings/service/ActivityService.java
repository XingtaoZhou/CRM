package com.zxt.settings.service;

import com.zxt.settings.domain.Activity;
import com.zxt.settings.domain.User;
import com.zxt.settings.vo.PageVo;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    List<User> getUserList();

    int saveActivity(Activity activity);

    PageVo<Activity> pageList(Map<String, Object> pageMap);
}
