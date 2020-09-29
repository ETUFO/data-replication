package com.rep.core.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @classname: DataReplicationMapper
 * @description: TODO
 * @author: wangye
 * @date: 2020/9/28 13:53
 **/
public interface DataReplicationMapper {

    List<Map> selectList(
            @Param("tableName")String tableName,
            @Param("paramName")String paramName,
            @Param("paramList")List paramList);
}
