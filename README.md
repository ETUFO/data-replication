## 使用场景
对于多个表数据进行复制，根据配置替换关联字段；
## 使用方式
1、pom.xml
```xml
       <dependency>
            <groupId>com.rep</groupId>
            <artifactId>data-replication-boot-starter</artifactId>
            <version>1.0</version>
        </dependency>
        <dependency>
            <groupId>com.rep</groupId>
            <artifactId>data-replication-mybatisplus</artifactId>
            <version>1.0</version>
        </dependency>
```
2、在resource路径下配置rep.xml文件，sample.xml配置范例

3、调用 com.rep.core.DataReplication#copy

## 支持
数据库：mysql

框架：mybatis-plus
