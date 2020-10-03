package com.zxt.settings.dao;

import com.zxt.settings.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> getRemarkById(String id);


    int delete(ClueRemark remark);
}
