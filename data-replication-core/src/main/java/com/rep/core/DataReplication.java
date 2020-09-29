package com.rep.core;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import com.rep.core.exception.NotConditionException;
import com.rep.core.mapper.DataReplicationMapper;
import com.rep.core.parse.ReplicationParse;
import com.rep.core.parse.model.DependTable;
import com.rep.core.parse.model.Table;
import com.rep.core.parse.model.Tables;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
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

    private final static Map<String,Class> ENTITY_CLASS_MAP = new ConcurrentHashMap<>();
    @Autowired
    private DataReplicationMapper mapper;

    public DataReplication(ReplicationParse parse){
        this.parse = parse;
    }

    /**
     * 数据复制，替换表之间关联字段数据
     * @auther wangye
     * @date 2020/9/29
     * @param param
     */
    public void copy(Map<String,Object> param){
//
//      SqlHelper.table(Object).getCurrentNamespace()
        //TODO 查询数据




        //TODO 替换数据
        //TODO 根据配置获取或接口获取实体Class对象
        //TODO 对外开发接口个性化修改数据
        //TODO 生成insert语句
        //TODO 保存数据


    }

    private String getBatchInsertSql(TableInfo tableInfo, Class<?> modelClass) {
        String batchInsertSql = "INSERT INTO %s (%s) %s";
        StringBuilder insertColumnBuilder = new StringBuilder();
        StringBuilder itemColumnBuilder = new StringBuilder();
        Field[] fields = modelClass.getDeclaredFields();


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
            itemColumnBuilder.append("#{item." + fieldName + "},");
        }
        if(insertColumnBuilder.charAt(insertColumnBuilder.length() - 1) == ','){
            insertColumnBuilder.deleteCharAt(insertColumnBuilder.length() - 1);
        }
        if(itemColumnBuilder.charAt(itemColumnBuilder.length() - 1) == ','){
            itemColumnBuilder.deleteCharAt(itemColumnBuilder.length() - 1);
        }
        String foreachSql = "values" +
                " <foreach collection=\"items\" item='item'  open='' index='index' separator=','>\n" +
                "(%s)</foreach>";
        foreachSql = String.format(foreachSql, itemColumnBuilder);
        return String.format(batchInsertSql, tableInfo.getTableName(), insertColumnBuilder, foreachSql);
    }
}
