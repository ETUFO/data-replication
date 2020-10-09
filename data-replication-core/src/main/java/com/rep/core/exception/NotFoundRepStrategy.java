package com.rep.core.exception;

/**
 * 没有找到替换策略
 * @author wangye
 * @classname NotConditionException
 * @date 2020/9/29 8:24
 **/
public class NotFoundRepStrategy extends RuntimeException {

    public NotFoundRepStrategy(){}

    public NotFoundRepStrategy(String message){
        super(message);
    }
}
