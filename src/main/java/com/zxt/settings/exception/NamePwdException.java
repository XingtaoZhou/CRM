package com.zxt.settings.exception;


//用户名密码错误异常
public class NamePwdException extends MyException{
    public NamePwdException() {
        super();
    }

    public NamePwdException(String message) {
        super(message);
    }
}
