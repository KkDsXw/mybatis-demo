package com.kk.mybatis.custom.v1.proxy;

import com.kk.mybatis.custom.v1.session.KkSqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Kk MapperProxy
 *
 * @Author kk.xie
 * @Date 2019/11/23 22:42
 * @Version 1.0
 **/
public class KkMapperProxy implements InvocationHandler {

    private KkSqlSession kkSqlSession;

    public KkMapperProxy(KkSqlSession kkSqlSession) {
        this.kkSqlSession = kkSqlSession;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return kkSqlSession.selectOne(method.getDeclaringClass().getName() + "." + method.getName(), String.valueOf(args[0]));
    }
}
