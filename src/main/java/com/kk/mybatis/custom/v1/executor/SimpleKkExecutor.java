package com.kk.mybatis.custom.v1.executor;

import com.kk.mybatis.custom.v1.config.KkConfiguration;
import com.kk.mybatis.custom.v1.executor.KkExecutor;
import com.kk.mybatis.model.Group;
import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * 监控执行器
 *
 * @Author kk.xie
 * @Date 2019/11/23 23:00
 * @Version 1.0
 **/
public class SimpleKkExecutor implements KkExecutor {

    private KkConfiguration kkConfiguration;

    public SimpleKkExecutor(KkConfiguration kkConfiguration) {
        this.kkConfiguration = kkConfiguration;
    }

    public <T> T query(String statement, String param) {
        String statementSql = kkConfiguration.getStatementMappingSql(statement);
        String querySql = statementSql.replace("?", param);
        return query(querySql);
    }

    private <T> T query(String querySql) {
        String url = null;
        String username = null;
        String password = null;
        String driver = null;

        try {
            String resource = "config.properties";
            Properties properties = Resources.getResourceAsProperties(resource);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            driver = properties.getProperty("driver");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Connection con= null;
        Statement st= null;
        ResultSet rs= null;
        try {
            //1.加载驱动程序
            Class.forName(driver);
            //2.获得数据库链接
            con = DriverManager.getConnection(url,username,password);
            //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            st = con.createStatement();
            rs = st.executeQuery(querySql);
            //4.处理数据库的返回结果(使用ResultSet类)
            Group group = new Group();
            while (rs.next()){
                group.setId(rs.getLong(1));
                group.setName(rs.getString(2));
            }
            return (T) group;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭资源
                rs.close();
                st.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
