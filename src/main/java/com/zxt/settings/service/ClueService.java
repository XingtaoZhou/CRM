package com.zxt.settings.service;

import com.zxt.settings.domain.Activity;
import com.zxt.settings.domain.Clue;
import com.zxt.settings.domain.User;
import com.zxt.settings.vo.PageVo;

import java.util.List;
import java.util.Map;

public interface ClueService {

    List<User> getUserList();

    Map<String, Object> save(Clue clue);

    PageVo<Clue> pageList(Map<String, Object> map);

    Clue detail(String id);

    List<Activity> getActivityListByClueId(String id);

    Map<String, Object> deleteCARById(String id);

    List<Activity> getActivityByName(Map<String,Object> map);
}
