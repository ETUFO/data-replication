package com.rep.core.parse;

import cn.hutool.core.io.FileUtil;
import com.rep.core.parse.model.Tables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.util.Optional;

/**
 * @author wangye
 * @classname ReplicationParse
 * @date 2020/9/25 15:54
 **/
@Slf4j
public class ReplicationParse {

    public final Tables tables;

    public ReplicationParse(String filePath){
        Object unmarshal = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource(filePath);
            File file = classPathResource.getFile();
            if (!file.exists()) {
                log.error(filePath + "文件不存在");
            }
            String content  = FileUtil.readString(file, "Utf-8");
            JAXBContext context = JAXBContext.newInstance(Tables.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshal = unmarshaller.unmarshal(new StringReader(content));
        } catch (Exception e) {
            log.error("解析" + filePath + "文件异常", e);
        }
        if (unmarshal == null) {
            log.error("获取" + filePath + "文件内容为空");
        }
        tables = Optional.ofNullable(unmarshal).map(u -> (Tables) u).orElse(null);
    }
}
