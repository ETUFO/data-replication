package com.rep.core.rep;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.rep.core.exception.NotFoundRepStrategy;
import com.rep.core.exception.RepFieldValueException;
import com.rep.core.parse.model.DependTable;
import com.rep.core.parse.model.RepField;
import com.rep.core.parse.model.Table;
import com.rep.core.parse.model.Tables;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 替换关联字段并根据配置的替换策略替换指定字段
 *
 * @author wangye
 * @classname RepFieldService
 * @date 2020/10/9 15:38
 **/
@Component
public class RepFieldService {

    public void repFields(Map<String, List<Map>> dataMap, Tables tables) {
        //{tableName:{fieldName:{oldValue:newValue}},.......}
        Map<String, Map<String, Map<String, String>>> fieldValueCorrespondingMap = Maps.newHashMap();
        Set<String> completedTableSet = Sets.newHashSetWithExpectedSize(tables.getTables().size());
        int count = 0;
        do {
            count = completedTableSet.size();
            for (Table table : tables.getTables()) {
                List<DependTable> dependTables = table.getDependTables();
                //检查依赖表中数据是否已替换
                if (!checkDependTableIsReplaced(fieldValueCorrespondingMap, dependTables)) {
                    continue;
                }
                //拼接要替换的关联字段数据 {sourceFieldName:{oldFieldValue:newFieldValue},...}
                Map<String,Map<String,String>> sourceFieldMap = Maps.newHashMap();
                for (DependTable dependTable : dependTables) {
                    Map<String, String> fieldValueMap = Optional.ofNullable(fieldValueCorrespondingMap
                            .get(dependTable.getTableName())).map(m->m.get(dependTable.getTargetField())).orElse(null);
                    sourceFieldMap.put(dependTable.getSourceField(),fieldValueMap);
                }
                List<Map> dataList = dataMap.get(table.getTableName());
                for (Map data : dataList) {
                    //替换关联字段
                    for(Map.Entry<String,Map<String,String>> entry: sourceFieldMap.entrySet()){
                        String sourceFiledName = entry.getKey();
                        Object oldFieldValue = data.get(sourceFiledName);
                        data.put(sourceFiledName,entry.getValue().get(oldFieldValue));
                    }
                    List<RepField> repFields = table.getRepFields();
                    //替换指定字段
                    if(CollectionUtil.isNotEmpty(repFields)){
                        for (RepField repField : repFields) {
                            String repStrategyBeanName = repField.getRepFieldStrategy();
                            RepFieldStrategy strategy = SpringUtil.getBean(repStrategyBeanName);
                            if(strategy==null){
                                throw new NotFoundRepStrategy(String.format("没有找到{}表{}字段的替换策略",table.getTableName(),repField.getFieldName()));
                            }
                            data.put(repField.getFieldName(),strategy.get());
                        }
                    }
                }
                completedTableSet.add(table.getTableName());
            }
        } while (completedTableSet.size() < tables.getTables().size() && count != completedTableSet.size());

        if(completedTableSet.size() != tables.getTables().size()){
            Set<String> tableNameSet = Sets.newHashSet();
            for (Table table : tables.getTables()) {
                if (!completedTableSet.contains(table.getTableName())){
                    tableNameSet.add(table.getTableName());
                }
            }
            throw new RepFieldValueException("{}表字段没有被替换", StringUtils.join(tableNameSet,","));
        }
    }

    public boolean checkDependTableIsReplaced(Map<String, Map<String, Map<String, String>>> fieldValueCorrespondingMap,
                                              List<DependTable> dependTables) {
        for (DependTable dependTable : dependTables) {
            if (fieldValueCorrespondingMap.get(dependTable.getTableName()) == null) {
                return false;
            }
        }
        return true;
    }
}
