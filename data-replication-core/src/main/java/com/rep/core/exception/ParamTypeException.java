package com.rep.core.exception;

/**
 * 参数类型异常
 * @author wangye
 * @classname NotConditionException
 * @date 2020/9/29 8:24
 **/
public class ParamTypeException extends RuntimeException {

    public ParamTypeException(){}

    public ParamTypeException(String message){
        super(message);
    }
}
