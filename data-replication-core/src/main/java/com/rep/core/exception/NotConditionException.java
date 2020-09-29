package com.rep.core.exception;

/**
 * 没有查询条件获取表中数据
 * @author wangye
 * @classname NotConditionException
 * @date 2020/9/29 8:24
 **/
public class NotConditionException extends RuntimeException {

    public NotConditionException(){}

    public NotConditionException(String message){
        super(message);
    }
}
