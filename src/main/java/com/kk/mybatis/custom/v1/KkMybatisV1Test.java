package com.kk.mybatis.custom.v1;

import com.kk.mybatis.custom.v1.config.KkConfiguration;
import com.kk.mybatis.custom.v1.executor.KkExecutor;
import com.kk.mybatis.custom.v1.executor.SimpleKkExecutor;
import com.kk.mybatis.custom.v1.mapper.KkMapper;
import com.kk.mybatis.custom.v1.session.KkSqlSession;
import com.kk.mybatis.model.Group;

/**
 * 自定义mybatis测试
 *
 * @Author kk.xie
 * @Date 2019/11/23 23:16
 * @Version 1.0
 **/
public class KkMybatisV1Test {
    public static void main(String[] args) {
        KkExecutor kkExecutor = new SimpleKkExecutor(new KkConfiguration());
        KkSqlSession kkSqlSession = new KkSqlSession(kkExecutor);

        KkMapper mapper = kkSqlSession.getMapper(KkMapper.class);
        Group group = mapper.selectById(1L);

        System.out.println(group);
    }
}
