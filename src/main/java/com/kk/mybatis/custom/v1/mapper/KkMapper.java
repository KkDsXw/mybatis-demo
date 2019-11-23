package com.kk.mybatis.custom.v1.mapper;

import com.kk.mybatis.model.Group;

/**
 * mapper
 *
 * @Author kk.xie
 * @Date 2019/11/23 22:53
 * @Version 1.0
 **/
public interface KkMapper {
    Group selectById(Long id);
}
