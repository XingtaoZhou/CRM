package com.zxt.settings.dao;

import com.zxt.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> selectByTypeCode(String type);
}
