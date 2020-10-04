package com.zxt.settings.dao;

import com.zxt.settings.domain.Customer;

import java.util.List;

public interface CustomerDao {
    Customer getCustomerByName(String company);

    int save(Customer customer);

    List<String> getCustomerName(String name);
}
