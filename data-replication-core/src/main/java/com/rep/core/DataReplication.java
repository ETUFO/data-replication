package com.rep.core;

import com.rep.core.parse.ReplicationParse;
import com.rep.core.parse.model.Tables;

/**
 * @author wangye
 * @classname DataReplication
 * @date 2020/9/25 15:52
 **/
public class DataReplication {

    private ReplicationParse parse;

    public DataReplication(ReplicationParse parse){
        this.parse = parse;
    }

    public void test(){
        Tables tables = parse.tables;
        System.out.println(1111);
    }
}
