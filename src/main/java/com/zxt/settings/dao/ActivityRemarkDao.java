package com.zxt.settings.dao;

import com.zxt.settings.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByIds(String[] ids);

    int deleteByIds(String[] ids);

    List<ActivityRemark> getRemarkById(String id);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark remark);

    int updateRemark(ActivityRemark remark);
}
