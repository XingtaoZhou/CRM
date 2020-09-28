package com.zxt.settings.exception;


//Ip地址受限异常
public class IpException extends MyException{
    public IpException() {
        super();
    }

    public IpException(String message) {
        super(message);
    }
}
