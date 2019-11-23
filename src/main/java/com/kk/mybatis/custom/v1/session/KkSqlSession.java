package com.kk.mybatis.custom.v1.session;

import com.kk.mybatis.custom.v1.executor.KkExecutor;
import com.kk.mybatis.custom.v1.proxy.KkMapperProxy;

import java.lang.reflect.Proxy;

/**
 * kk sqlSession
 *
 * @Author kk.xie
 * @Date 2019/11/23 22:38
 * @Version 1.0
 **/
public class KkSqlSession {
    private KkExecutor kkExecutor;

    public KkSqlSession(KkExecutor kkExecutor) {
        this.kkExecutor = kkExecutor;
    }

    public <T> T getMapper(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new KkMapperProxy(this));
    }

    public <T> T selectOne(String statement, String param){
        return kkExecutor.query(statement, param);
    }
}
