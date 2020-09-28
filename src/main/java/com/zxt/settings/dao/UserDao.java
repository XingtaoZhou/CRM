package com.zxt.settings.dao;

import com.zxt.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    User selectUser(Map<String,Object> map);

    List<User> getUserList();
}
