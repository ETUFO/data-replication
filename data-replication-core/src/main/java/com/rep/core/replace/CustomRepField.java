package com.rep.core.replace;

import java.util.List;
import java.util.Map;

/**
 * 自定义字段替换接口
 * @classname: CustomRepField
 * @author: wangye
 * @date: 2020/10/10 10:00
 **/
public interface CustomRepField {

    void replace(Map<String, List<Map>> dataMap);
}
