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

    //当前模块所依赖的模块
    @XmlElement(name="depend-table")
    private List<DependTable> dependTables;

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
}
