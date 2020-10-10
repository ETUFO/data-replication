package com.rep.core.parse.model;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
@XmlRootElement
public class Tables implements Serializable {

    private static final long serialVersionUID = 8713956991962097670L;

    //通用配置
    @XmlElement(name="configuration")
    private Configuration configuration;

    //当前模块所依赖的模块
    @XmlElement(name="table")
    private List<Table> tables;

    @XmlTransient
    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    @XmlTransient
    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
