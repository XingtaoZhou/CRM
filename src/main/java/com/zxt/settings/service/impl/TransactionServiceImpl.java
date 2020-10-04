package com.zxt.settings.service.impl;

import com.zxt.settings.dao.CustomerDao;
import com.zxt.settings.dao.UserDao;
import com.zxt.settings.domain.User;
import com.zxt.settings.service.TransactionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class TransactionServiceImpl implements TransactionService {

    @Resource
    private UserDao userDao;

    @Resource
    private CustomerDao customerDao;

    @Override
    public List<User> getUserList() {

        return userDao.getUserList();

    }

    @Override
    public List<String> getCustomerName(String name) {

        return customerDao.getCustomerName(name);

    }
}
