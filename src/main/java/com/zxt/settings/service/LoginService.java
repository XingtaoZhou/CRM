package com.zxt.settings.service;

import com.zxt.settings.domain.User;
import com.zxt.settings.exception.MyException;

public interface LoginService {

    User login(String loginAct, String loginPwd, String ip) throws MyException;
}
