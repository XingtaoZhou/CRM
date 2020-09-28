package com.zxt.settings.exception;


//账号锁定异常
public class ActLockException extends MyException{
    public ActLockException() {
        super();
    }

    public ActLockException(String message) {
        super(message);
    }
}
