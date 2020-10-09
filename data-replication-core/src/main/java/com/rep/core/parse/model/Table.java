package com.rep.core.parse.model;

import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 * 设计模块
 * @author wangye
 * @classname Module
 * @date 2020/9/9 15:46
 **/
@ToString
public class Table implements Serializable {

    private static final long serialVersionUID = 8713956991962097670L;
    //设计模块对应的表名
    @XmlAttribute(name = "table-name")
    private String tableName;

    //查询字段
    @XmlAttribute(name = "query-field")
    private String queryField;

    //查询参数名
    @XmlAttribute(name = "param-name")
    private String paramName;

    //实体类
    @XmlAttribute(name = "entity-class")
    private String entityClass;

    //当前模块所依赖的模块
    @XmlElement(name="depend-table")
    private List<DependTable> dependTables;

    //当前模块所依赖的模块
    @XmlElement(name="rep-field")
    private List<RepField> repFields;

    @XmlTransient
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @XmlTransient
    public List<DependTable> getDependTables() {
        return dependTables;
    }

    public void setDependTables(List<DependTable> dependTables) {
        this.dependTables = dependTables;
    }

    @XmlTransient
    public String getQueryField() {
        return queryField;
    }

    public void setQueryField(String queryField) {
        this.queryField = queryField;
    }

    @XmlTransient
    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @XmlTransient
    public String getEntityClass() {
        return entityClass;
    }

    @XmlTransient
    public List<RepField> getRepFields() {
        return repFields;
    }

    public void setRepFields(List<RepField> repFields) {
        this.repFields = repFields;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }
}
