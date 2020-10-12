package com.rep.mybatisplus.listener;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.google.common.collect.Maps;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class DataRepApplicationRunner implements ApplicationRunner {

	public final static Map<String, TableInfo> TABLE_INFO_MAP = Maps.newHashMap();

	@Override
	public void run(ApplicationArguments args) throws Exception {
		List<TableInfo> tableInfos = TableInfoHelper.getTableInfos();
		TABLE_INFO_MAP.putAll(tableInfos.stream().collect(Collectors.toMap(TableInfo::getTableName, t -> t)));
	}

}
