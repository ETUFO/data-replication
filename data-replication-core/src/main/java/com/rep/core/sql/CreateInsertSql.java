package com.rep.core.sql;

import com.rep.core.parse.model.Tables;

import java.util.List;
import java.util.Map;

/**
 * 生成insert语句
 * @classname: CreateInsertSql
 * @author: wangye
 * @date: 2020/10/10 13:31
 **/
public interface CreateInsertSql {

    List<String> createInsertSql(Map<String,List<Map>> dataMap, Tables tables)
            throws ClassNotFoundException;
}
