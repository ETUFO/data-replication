package com.rep.core.query;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;
import com.rep.core.exception.NotConditionException;
import com.rep.core.exception.ParamTypeException;
import com.rep.core.mapper.DataReplicationMapper;
import com.rep.core.parse.model.DependTable;
import com.rep.core.parse.model.Table;
import com.rep.core.parse.model.Tables;
import com.rep.core.repository.DataOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 查询要复制的数据
 *
 * @author wangye
 * @classname DataQuery
 * @date 2020/9/29 8:27
 **/
@Component
@Slf4j
public class DataQuery {

    @Autowired
    private DataOperation dataOperation;

    public Map<String, List<Map>> queryData(Map<String, Object> param, Tables tables) {
        List<Table> tableList = tables.getTables();
        int completedCount = 0;
        int oldCompletedCount = 0;
        Map<String, List<Map>> dataContainer = Maps.newHashMap();
        do {
            oldCompletedCount = completedCount;
            for (Table table : tableList) {
                List<Map> datas = null;
                String paramName = table.getParamName();
                if (StringUtils.isNotBlank(paramName)) {
                    Object paramValue = param.get(paramName);
                    if (paramValue == null) {
                        throw new NotConditionException(String.format("找不到参数值，tableName：%s，paramName: %s", table.getTableName(), paramName));
                    }
                    if (paramValue instanceof Collection) {
                        List paramList = new ArrayList((Collection) paramValue);
                        datas = dataOperation.selectList(table.getTableName(), table.getQueryField(), paramList);
                    } else if (paramValue.getClass().isPrimitive() || paramValue instanceof String) {
                        datas = dataOperation.selectList(table.getTableName(), table.getQueryField(), Arrays.asList(paramValue));
                    } else {
                        throw new ParamTypeException(String.format("参数类型只支持集合与基本类型，tableName：%s，paramName：%s", table.getTableName(), paramName));
                    }
                } else {
                    List<DependTable> dependTables = table.getDependTables();
                    if (CollectionUtil.isNotEmpty(dependTables)) {
                        DependTable dependTable = dependTables.get(0);
                        List<Map> refDataList = dataContainer.get(dependTable.getTableName());
                        if (CollectionUtil.isNotEmpty(refDataList)) {
                            String targetField = dependTable.getTargetField();
                            List<Object> refFieldList = refDataList.stream().map(m -> m.get(targetField)).collect(Collectors.toList());
                            datas = dataOperation.selectList(table.getTableName(), dependTable.getSourceField(), refFieldList);
                        } else {
                            continue;
                        }
                    } else {
                        throw new NotConditionException(String.format(
                                "既没有配置param-name属性也没配置depend-table子标签无法查找数据，tableName: %s", table.getTableName()));
                    }
                }
                if (CollectionUtil.isNotEmpty(datas)) {
                    dataContainer.put(table.getTableName(), datas);
                    completedCount++;
                } else {
                    log.info("获取数据为空，tableName：%s", table.getTableName());
                }
            }

        } while (completedCount < tableList.size() && oldCompletedCount != completedCount);
        return dataContainer;
    }
}
