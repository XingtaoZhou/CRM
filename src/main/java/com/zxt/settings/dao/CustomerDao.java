package com.zxt.settings.dao;

import com.zxt.settings.domain.Customer;

public interface CustomerDao {
    Customer getCustomerByName(String company);

    int save(Customer customer);
}
