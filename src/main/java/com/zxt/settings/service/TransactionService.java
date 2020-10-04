package com.zxt.settings.service;

import com.zxt.settings.domain.User;

import java.util.List;

public interface TransactionService {
    List<User> getUserList();

    List<String> getCustomerName(String name);
}
