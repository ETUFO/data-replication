package com.rep.core.parse.model;

import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * @author wangye
 * @classname RepField
 * @date 2020/10/9 11:00
 **/
@ToString
public class RepField implements Serializable {

    @XmlAttribute(name="field-name")
    private String fieldName;
    @XmlAttribute(name="rep-field-strategy")
    private String repFieldStrategy;

    @XmlTransient
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @XmlTransient
    public String getRepFieldStrategy() {
        return repFieldStrategy;
    }

    public void setRepFieldStrategy(String repFieldStrategy) {
        this.repFieldStrategy = repFieldStrategy;
    }
}
