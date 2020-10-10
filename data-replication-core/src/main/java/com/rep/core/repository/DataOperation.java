package com.rep.core.repository;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 操作数据库
 * @classname: DataQuery
 * @description: TODO
 * @author: wangye
 * @date: 2020/10/10 13:40
 **/
public interface DataOperation {

    /**
     * 动态拼接sql获取数据
     * @auther wangye
     * @date 2020/10/10
     * @param tableName
     * @param paramName
     * @param paramList
     */
    List<Map> selectList(String tableName,String paramName,List paramList);

    /**
     * 批量插入sql语句
     * @auther wangye
     * @date 2020/10/10
     * @param sql
     */
    void insert(String sql);
}
