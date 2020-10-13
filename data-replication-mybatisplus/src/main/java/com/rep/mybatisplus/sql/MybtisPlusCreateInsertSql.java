package com.rep.mybatisplus.sql;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.rep.core.parse.model.Table;
import com.rep.core.parse.model.Tables;
import com.rep.core.sql.CreateInsertSql;
import com.rep.mybatisplus.listener.DataRepApplicationRunner;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author wangye
 * @classname MybtisPlusCreateInsertSql
 * @date 2020/10/10 13:32
 **/
@Component
public class MybtisPlusCreateInsertSql implements CreateInsertSql {



    @Override
    public List<String> createInsertSql(Map<String, List<Map>> dataMap, Tables tables) throws ClassNotFoundException {
        List<String> insertSqlList = Lists.newArrayListWithCapacity(tables.getTables().size());
        for (Table table : tables.getTables()) {
            Class<?> clazz = DataRepApplicationRunner.TABLE_INFO_MAP.get(table.getTableName()).getEntityType();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
            String insertSql = getBatchInsertSql(tableInfo, clazz, dataMap.get(table.getTableName()));
            insertSqlList.add(insertSql);
        }
        return insertSqlList;
    }

    private String getBatchInsertSql(TableInfo tableInfo, Class<?> modelClass, List<Map> dataList) {

        String batchInsertSql = "INSERT INTO %s (%s) %s";
        StringBuilder insertColumnBuilder = new StringBuilder();
        Field[] fields = modelClass.getDeclaredFields();

        List<List<Object>> valueList = Lists.newArrayListWithCapacity(dataList.size());
        for (int i = 0; i < dataList.size(); i++) {
            valueList.add(Lists.newArrayList());
        }
        //添加column
        for (int i = 0, len = fields.length; i < len; i++) {
            Field field = fields[i];
            //过滤静态变量
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            //过滤注解说明非持久化列
            TableField annotation = field.getAnnotation(TableField.class);
            if (annotation != null && !annotation.exist()) {
                continue;
            }
            String fieldName = field.getName();
            insertColumnBuilder.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName)).append(",");
            for (int j = 0; j < dataList.size(); j++) {
                List values = valueList.get(j);
                values = values == null ? Lists.newArrayList() : values;
                fieldName = convertFieldName(tableInfo, fieldName);
                Object value = dataList.get(j).get(fieldName);
                if (value instanceof String) {
                    value = "'" + value + "'";
                } else if (value == null) {
                    value = "null";
                }
                values.add(value);
            }
        }

        List<String> valueStrList = Lists.newArrayListWithCapacity(valueList.size());
        for (List<Object> list : valueList) {
            StringBuilder s = new StringBuilder("(")
                    .append(StringUtils.join(list, ",")).append(")");
            valueStrList.add(s.toString());
        }

        String valueStr = " values " + StringUtils.join(valueStrList, ",") + ";";

        if (insertColumnBuilder.charAt(insertColumnBuilder.length() - 1) == ',') {
            insertColumnBuilder.deleteCharAt(insertColumnBuilder.length() - 1);
        }

        return String.format(batchInsertSql, tableInfo.getTableName(), insertColumnBuilder, valueStr);
    }

    private String convertFieldName(TableInfo tableInfo, String fieldName) {
        boolean mapUnderscoreToCamelCase = tableInfo.getConfiguration().isMapUnderscoreToCamelCase();
        if (!mapUnderscoreToCamelCase) {
            return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
        }
        return fieldName;
    }

}
