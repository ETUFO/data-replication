package com.rep.core.parse.model;

import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * 被依赖模块
 * @author wangye
 * @classname CidsCourseMoule
 * @date 2020/9/9 15:46
 **/
@ToString
public class DependTable implements Serializable {

    private static final long serialVersionUID = 8713956991962097670L;
    //设计模块对应的表名
    @XmlAttribute(name = "table-name")
    private String tableName;
    //依赖当前模块的关联字段
    @XmlAttribute(name = "source-field")
    private String sourceField;

    @XmlTransient
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @XmlTransient
    public String getSourceField() {
        return sourceField;
    }

    public void setSourceField(String sourceField) {
        this.sourceField = sourceField;
    }

}
