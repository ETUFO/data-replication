package com.rep.core;

/**
 * 根据表名转换为实体类class对象
 * @classname: TableConvertEntity
 * @author: wangye
 * @date: 2020/9/28 14:42
 **/
public interface TableConvertEntity {

    Class convert(String tableName);
}
