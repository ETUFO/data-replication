package com.rep.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @classname: DataReplicationMapper
 * @description: TODO
 * @author: wangye
 * @date: 2020/9/28 13:53
 **/
public interface DataReplicationMapper extends BaseMapper {

    List<Map> selectList(
            @Param("tableName")String tableName,
            @Param("paramName")String paramName,
            @Param("paramList")List paramList);

    void batchInsert(String insertSql);
}
