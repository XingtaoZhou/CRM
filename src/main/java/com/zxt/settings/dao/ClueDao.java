package com.zxt.settings.dao;

import com.zxt.settings.domain.Activity;
import com.zxt.settings.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {
    int save(Clue clue);

    int countPageList(Map<String, Object> map);

    List<Clue> pageList(Map<String, Object> map);

    Clue detail(String id);

    List<Activity> getActivityListByClueId(String id);

    Clue getClueById(String clueId);

    int delete(String clueId);
}
