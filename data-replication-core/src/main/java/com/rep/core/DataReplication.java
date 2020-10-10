package com.rep.core;

import com.rep.core.parse.ReplicationParse;
import com.rep.core.parse.model.Tables;
import com.rep.core.query.DataQuery;
import com.rep.core.replace.CustomRepField;
import com.rep.core.replace.RepFieldService;
import com.rep.core.repository.DataOperation;
import com.rep.core.sql.CreateInsertSql;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangye
 * @classname DataReplication
 * @date 2020/9/25 15:52
 **/
public class DataReplication {

    private ReplicationParse parse;

    private final static Map<String, Class> ENTITY_CLASS_MAP = new ConcurrentHashMap<>();
    @Autowired
    private DataQuery dataQuery;
    @Autowired
    private RepFieldService repFieldService;
    @Autowired(required = false)
    private CustomRepField customRepField;
    @Autowired
    private CreateInsertSql createInsertSql;
    @Autowired
    private DataOperation dataOperation;


    public DataReplication(ReplicationParse parse) {
        this.parse = parse;
    }

    /**
     * 数据复制，替换表之间关联字段数据
     * @param param
     * @auther wangye
     * @date 2020/9/29
     */
    public void copy(Map<String, Object> param) throws ClassNotFoundException {
        //查询要复制的数据
        Tables tables = parse.tables;
        Map<String, List<Map>> dataMap = dataQuery.queryData(param, tables);
        //替换关联关系字段并根据配置策略替换指定字段
        repFieldService.replace(dataMap, tables);
        //对外开发接口自定义修改数据
        if (customRepField != null) {
            customRepField.replace(dataMap);
        }
        //生成insert语句
        List<String> insertSql = createInsertSql.createInsertSql(dataMap, tables);
        //保存数据
        for (String sql : insertSql) {
            dataOperation.insert(sql);
        }
    }


}
