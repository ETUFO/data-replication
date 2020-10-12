package com.rep.autoconfigure;

import com.rep.core.DataReplication;
import com.rep.core.parse.ReplicationParse;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author wangye
 * @classname DataReplicationAutoConfiguration
 * @date 2020/9/25 15:39
 **/
@Configuration
@EnableConfigurationProperties(DataReplicationProperties.class)
@ComponentScan({"com.rep","cn.hutool"})
public class DataReplicationAutoConfiguration {

    DataReplicationProperties properties;
    ReplicationParse parse;

    public DataReplicationAutoConfiguration(DataReplicationProperties properties) {
        this.properties = properties;
        this.parse = new ReplicationParse(this.properties.getFilePath());
    }

    @Bean
    public DataReplication dataReplication() {
        return new DataReplication(parse);
    }
}
