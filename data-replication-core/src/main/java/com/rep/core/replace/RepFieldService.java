package com.rep.core.replace;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.rep.core.exception.NotFoundRepStrategy;
import com.rep.core.exception.RepFieldValueException;
import com.rep.core.parse.model.*;
import com.rep.core.util.SpringUtil;
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

    public void replace(Map<String, List<Map>> dataMap, Tables tables) {
        //{tableName:{fieldName:{oldValue:newValue}},.......}
        Map<String, Map<String, Map<Object, Object>>> fieldValueCorrespondingMap = Maps.newHashMap();
        Set<String> completedTableSet = Sets.newHashSetWithExpectedSize(tables.getTables().size());
        Configuration configuration = tables.getConfiguration();
        List<RepField> configRepFieldList = Optional.ofNullable(configuration.getRepFields())
                .map(f->Optional.ofNullable(f.getRepFieldList()).orElse(Lists.newArrayList()))
                .orElse(Lists.newArrayList());
        int count = 0;
        do {
            count = completedTableSet.size();
            for (Table table : tables.getTables()) {
                if (completedTableSet.contains(table.getTableName())) {
                    continue;
                }
                List<DependTable> dependTables = Optional.ofNullable(table.getDependTables()).map(d -> d).orElse(Lists.newArrayList());
                //检查依赖表中数据是否已替换
                if (!checkDependTableIsReplaced(fieldValueCorrespondingMap, dependTables)) {
                    continue;
                }
                //拼接要替换的关联字段数据 {sourceFieldName:{oldFieldValue:newFieldValue},...}
                Map<String, Map<Object, Object>> sourceFieldMap = Maps.newHashMap();
                for (DependTable dependTable : dependTables) {
                    Map<Object, Object> fieldValueMap = Optional.ofNullable(fieldValueCorrespondingMap
                            .get(dependTable.getTableName())).map(m -> m.get(dependTable.getTargetField())).orElse(null);
                    sourceFieldMap.put(dependTable.getSourceField(), fieldValueMap);
                }
                List<Map> dataList = dataMap.get(table.getTableName());
                for (Map data : dataList) {
                    //替换关联字段
                    for (Map.Entry<String, Map<Object, Object>> entry : sourceFieldMap.entrySet()) {
                        String sourceFiledName = entry.getKey();
                        Object oldFieldValue = data.get(sourceFiledName);
                        if(oldFieldValue == null){
                            sourceFiledName = convertFieldName(sourceFiledName);
                            oldFieldValue = data.get(sourceFiledName);
                        }
                        data.put(sourceFiledName, entry.getValue().get(oldFieldValue));
                    }
                    List<RepField> repFields = Optional.ofNullable(table.getRepFields()).map(t->t).orElse(Lists.newArrayList());
                    repFields.addAll(configRepFieldList);
                    //替换指定字段
                    if (CollectionUtil.isNotEmpty(repFields)) {
                        for (RepField repField : repFields) {
                            Map<String,Map<Object, Object>> tableFieldMap = fieldValueCorrespondingMap.get(table.getTableName());
                            if(tableFieldMap == null){
                                tableFieldMap = Maps.newHashMap();
                                fieldValueCorrespondingMap.put(table.getTableName(),tableFieldMap);
                            }
                            Map fieldMap =  tableFieldMap.get(repField.getFieldName());
                            if(fieldMap == null){
                                fieldMap = Maps.newHashMap();
                                tableFieldMap.put(repField.getFieldName(),fieldMap);
                            }
                            String repStrategyBeanName = repField.getRepFieldStrategy();
                            RepFieldStrategy strategy = SpringUtil.getBean(repStrategyBeanName);
                            if (strategy == null) {
                                throw new NotFoundRepStrategy(String.format("没有找到%s表%s字段的替换策略",
                                        table.getTableName(), repField.getFieldName()));
                            }
                            //保存字段新旧值对应关系
                            fieldMap.put(data.get(repField.getFieldName()),strategy.get());
                            //替换字段值
                            data.put(repField.getFieldName(), fieldMap.get(data.get(repField.getFieldName())));
                        }
                    }
                }
                completedTableSet.add(table.getTableName());
            }
        } while (completedTableSet.size() < tables.getTables().size() && count != completedTableSet.size());

        if (completedTableSet.size() != tables.getTables().size()) {
            Set<String> tableNameSet = Sets.newHashSet();
            for (Table table : tables.getTables()) {
                if (!completedTableSet.contains(table.getTableName())) {
                    tableNameSet.add(table.getTableName());
                }
            }
            throw new RepFieldValueException("%s表字段没有被替换", StringUtils.join(tableNameSet, ","));
        }
    }

    private String convertFieldName(String sourceFiledName) {
        Converter<String, String> converter = sourceFiledName.contains("_")
                ? CaseFormat.LOWER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL)
                :CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);
        sourceFiledName = converter.convert(sourceFiledName);
        return sourceFiledName;
    }

    public boolean checkDependTableIsReplaced(Map fieldValueCorrespondingMap, List<DependTable> dependTables) {
        for (DependTable dependTable : dependTables) {
            if (fieldValueCorrespondingMap.get(dependTable.getTableName()) == null) {
                return false;
            }
        }
        return true;
    }
}
