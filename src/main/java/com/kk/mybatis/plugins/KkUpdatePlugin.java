package com.kk.mybatis.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * 自定义 拦截查询的插件，将拦截 Executor.update 方法
 *
 * @Author kk.xie
 * @Date 2019/11/20 16:13
 * @Version 1.0
 **/
@Intercepts({@Signature(
        type= Executor.class,
        method = "update",
        args = {MappedStatement.class,Object.class})})
public class KkUpdatePlugin implements Interceptor {

    public Object intercept(Invocation invocation) throws Throwable {
        // 1. 获取sql命令类型
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType commandType = ms.getSqlCommandType();

        // 2. 获取当前正在被操作的类, 有可能是Java Bean, 也可能是普通的操作对象, 比如普通的参数传递
        // 普通参数, 即是 @Param 包装或者原始 Map 对象, 普通参数会被 Mybatis 包装成 Map 对象
        // 即是 org.apache.ibatis.binding.MapperMethod$ParamMap
        Object parameter = invocation.getArgs()[1];
        // 获取拦截器指定的方法类型, 通常需要拦截 update
        String methodName = invocation.getMethod().getName();

        System.out.println("KkUpdatePlugin, methodName; " + methodName +", commandType: " + commandType);

        if (parameter instanceof Map) {
            // 3. @Param 等包装类
            // 更新时指定某些字段的最新数据值
            if (commandType.equals(SqlCommandType.UPDATE)) {
                //
                Map map = (Map) parameter;
                if (map.containsKey("modifier") && map.get("modifier") == null) {
                    map.put("modifier", "kkds");
                }
                if (map.containsKey("modifyTime") && map.get("modifyTime") == null) {
                    map.put("modifyTime", new Date());
                }
            }
        }
        // 4. 均不是需要被拦截的类型, 不做操作
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        // 调用插件
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {

    }
}
