package com.kk.mybatis.custom.v1.executor;

/**
 * 执行器接口
 *
 * @Author kk.xie
 * @Date 2019/11/23 22:40
 * @Version 1.0
 **/
public interface KkExecutor {
    <T> T query(String statement, String param);
}
