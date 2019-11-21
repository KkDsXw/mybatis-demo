package com.kk.mybatis.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 自定义 类型处理器
 * 使用上述的类型处理器将会【覆盖】已经存在的处理 Java 的 String 类型属性和 VARCHAR 参数及结果的类型处理器
 *
 * @Author kk.xie
 * @Date 2019/11/20 15:13
 * @Version 1.0
 **/
@MappedJdbcTypes(JdbcType.VARCHAR)
public class KkTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter + "-kk");
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName) + "-kk";
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex) + "-kk";
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex) + "-kk";
    }
}
