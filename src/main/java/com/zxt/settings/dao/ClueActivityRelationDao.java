package com.zxt.settings.dao;

import com.zxt.settings.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {
    int deleteCARById(String id);

    int insert(ClueActivityRelation car);

    List<ClueActivityRelation> getRelation(String clueId);
}
