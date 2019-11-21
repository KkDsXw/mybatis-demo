package com.kk.mybatis.config;

import com.kk.mybatis.mapper.GroupMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * mybatis 非xml配置
 *
 * @Author kk.xie
 * @Date 2019/11/20 11:44
 * @Version 1.0
 **/
public class MybatisConfiguration {
    public SqlSessionFactory getSqlSessionFactory(){
        DataSource dataSource = getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(GroupMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        return sqlSessionFactory;
    }

    private DataSource getDataSource() {
        PooledDataSourceFactory pooledDataSourceFactory = null;
        try {
            pooledDataSourceFactory = new PooledDataSourceFactory();
            String resource = "config.properties";
            Properties properties = Resources.getResourceAsProperties(resource);
            pooledDataSourceFactory.setProperties(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pooledDataSourceFactory.getDataSource();
    }
}
