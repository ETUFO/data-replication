package com.rep.autoconfigure;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置文件信息
 * @author wangye
 * @classname DataReplicationProperties
 * @date 2020/9/27 10:24
 **/
@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "data-rep")
public class DataReplicationProperties {

    private String filePath = "rep.xml";
}
