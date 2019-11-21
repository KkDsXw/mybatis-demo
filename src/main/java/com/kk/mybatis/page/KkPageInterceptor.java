package com.kk.mybatis.page;

import com.github.pagehelper.PageException;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * 自定义分页插件
 *
 * @Author kk.xie
 * @Date 2019/11/21 11:07
 * @Version 1.0
 **/
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
public class KkPageInterceptor implements Interceptor {
    private volatile KkPageHelper kkPageHelper;

    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();

        Executor executor = (Executor) invocation.getTarget();

        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        BoundSql boundSql = ms.getBoundSql(parameter);

        RowBounds rowBounds = (RowBounds) args[2];
        CacheKey pageKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);

        ResultHandler resultHandler = (ResultHandler) args[3];

        String pageSql = kkPageHelper.getPageSql(boundSql.getSql());

        BoundSql pageBoundSql = new BoundSql(
                ms.getConfiguration(), pageSql, boundSql.getParameterMappings(), parameter);

        return executor.query(ms, parameter, RowBounds.DEFAULT, resultHandler, pageKey, pageBoundSql);
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        try {
            Class<?> aClass = Class.forName("com.kk.mybatis.page.KkPageHelper");
            kkPageHelper = (KkPageHelper) aClass.newInstance();
        } catch (Exception e) {
            throw new PageException(e);
        }
    }
}
