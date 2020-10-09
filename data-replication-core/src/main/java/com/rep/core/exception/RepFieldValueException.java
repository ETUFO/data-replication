package com.rep.core.exception;

/**
 * 表中字段值没有替换
 *
 * @author wangye
 * @classname NotConditionException
 * @date 2020/9/29 8:24
 **/
public class RepFieldValueException extends RuntimeException {

    public RepFieldValueException() {
    }

    public RepFieldValueException(String message) {
        super(message);
    }

    public RepFieldValueException(String format, Object... args) {
        super(String.format(format, args));
    }
}
