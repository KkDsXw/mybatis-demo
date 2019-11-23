package com.kk.mybatis.custom.v1.config;

import java.util.HashMap;
import java.util.Map;

/**
 * mapper配置类
 * 记录查询方法与sql的映射关系
 *
 * @Author kk.xie
 * @Date 2019/11/23 22:39
 * @Version 1.0
 **/
public class KkConfiguration {
    private static Map<String, String> statementMapping = new HashMap();

    public String getStatementMappingSql(String statement) {
        return statementMapping.get(statement);
    }

    static {
        statementMapping.put("com.kk.mybatis.custom.v1.mapper.KkMapper.selectById", "select * from tbl_group where id = ?");
    }
}
