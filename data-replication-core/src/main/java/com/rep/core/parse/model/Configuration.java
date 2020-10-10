package com.rep.core.parse.model;

import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author wangye
 * @classname Configuration
 * @date 2020/10/10 15:28
 **/
@ToString
public class Configuration {

    @XmlElement(name = "rep-fields")
    private RepFields repFields;

    @XmlTransient
    public RepFields getRepFields() {
        return repFields;
    }

    public void setRepFields(RepFields repFields) {
        this.repFields = repFields;
    }
}
