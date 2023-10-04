package com.lyle.train.common.exception;

//业务异常类
public class BusinessException extends  RuntimeException{
    public BusinessExceptionEnum getE() {
        return e;
    }

    public void setE(BusinessExceptionEnum e) {
        this.e = e;
    }

    public BusinessException(BusinessExceptionEnum e) {
        this.e = e;
    }

    private BusinessExceptionEnum e;
}
