package com.zxt.settings.service;

import com.zxt.settings.domain.Tran;
import com.zxt.settings.domain.TranHistory;
import com.zxt.settings.domain.User;

import java.util.List;

public interface TransactionService {
    List<User> getUserList();

    List<String> getCustomerName(String name);

    Boolean save(Tran tran, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String id);
}
