package com.zxt.settings.dao;

import com.zxt.settings.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {
    int save(TranHistory tranHistory);

    List<TranHistory> getHistoryListByTranId(String id);
}
