package com.rep.core.repository.impl;

import com.rep.core.mapper.DataReplicationMapper;
import com.rep.core.repository.DataOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author wangye
 * @classname DataOperationImpl
 * @date 2020/10/10 13:45
 **/
@Component
public class DataOperationImpl implements DataOperation {

    @Autowired
    private DataReplicationMapper mapper;

    @Override
    public List<Map> selectList(String tableName, String paramName, List paramList) {
        return mapper.selectList(tableName, paramName, paramList);
    }

    @Override
    public void insert(String sql) {
        mapper.batchInsert(sql);
    }
}
