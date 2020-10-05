package com.zxt.settings.dao;

import com.zxt.settings.domain.Tran;

public interface TranDao {
    int save(Tran tran);

    Tran detail(String id);
}
