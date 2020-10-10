package com.rep.mybatisplus.repository;

import com.rep.core.repository.DataOperation;
import com.rep.mybatisplus.mapper.DataReplicationMapper;
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
public class MybatisPlusDataOperationImpl implements DataOperation {

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
