package com.rep.core.parse.model;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * @author wangye
 * @classname RepFields
 * @date 2020/10/10 15:26
 **/
@ToString
public class RepFields {

    @XmlElement(name="rep-field")
    private List<RepField> repFieldList;

    @XmlTransient
    public List<RepField> getRepFieldList() {
        return repFieldList;
    }

    public void setRepFieldList(List<RepField> repFieldList) {
        this.repFieldList = repFieldList;
    }
}
