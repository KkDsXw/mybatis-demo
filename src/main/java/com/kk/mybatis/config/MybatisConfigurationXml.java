package com.kk.mybatis.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * mybatis xml 配置文件
 *
 * @Author kk.xie
 * @Date 2019/11/20 9:44
 * @Version 1.0
 **/
public class MybatisConfigurationXml {

    public SqlSessionFactory getSqlSessionFactory(){
        SqlSessionFactory sqlSessionFactory = null;
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sqlSessionFactory;
    }
}
