package com.zxt.settings.service;

import com.zxt.settings.domain.*;
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

    Map<String, Object> bund(String cid, String[] ids);

    List<Activity> searchActivityByName(String name);

    void convert(String clueId, Tran tran, String createBy);

    List<ClueRemark> getRemarkById(String id);
}
