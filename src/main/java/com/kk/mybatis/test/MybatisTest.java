package com.kk.mybatis.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kk.mybatis.config.MybatisConfiguration;
import com.kk.mybatis.config.MybatisConfigurationXml;
import com.kk.mybatis.mapper.GroupMapper;
import com.kk.mybatis.model.Group;
import com.kk.mybatis.page.KkPageHelper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 配置单元测试
 * SqlSessionFactoryBuilder     方法中使用                    用完即弃
 * SqlSessionFactory            Application中使用，单例       启动时初始化
 * SqlSession                   方法中使用，会话级别           非线程安全,用时即取，用完即关
 *
 * @Author kk.xie
 * @Date 2019/11/20 14:03
 * @Version 1.0
 **/
public class MybatisTest {

    private SqlSessionFactory sqlSessionFactory;
    /**
     * 测试mybatis非XML配置
     *
     * @author kk.xie
     * @date 2019/11/20 14:07
     */
    @Test
    public void getSqlSessionFactory(){
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        SqlSessionFactory sqlSessionFactory = mybatisConfiguration.getSqlSessionFactory();
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * 测试mybatis非XML配置
     *
     * @author kk.xie
     * @date 2019/11/20 14:08
     */
    @Before
    public void getSqlSessionFactoryXml(){
        MybatisConfigurationXml mybatisConfigurationXml = new MybatisConfigurationXml();
        SqlSessionFactory sqlSessionFactory = mybatisConfigurationXml.getSqlSessionFactory();
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /**
     * 查询，使用完整方法路径进行映射
     * 唯一时可以使用selectByPrimaryKey，而不需要完整方法路径
     *
     * @author kk.xie
     * @date 2019/11/20 14:34
     */
    @Test
    public void selectOne(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Group group = sqlSession.selectOne("com.kk.mybatis.mapper.GroupMapper.selectByPrimaryKey", 1L);
        Assert.assertEquals(1L, group.getId().longValue());
    }

    /**
     * 查询，通过映射类获取类中的方法，推荐，更加安全
     *
     * @author kk.xie
     * @date 2019/11/20 14:34
     */
    @Test
    public void selectOneByMapper(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        GroupMapper mapper = sqlSession.getMapper(GroupMapper.class);
        Group group = mapper.selectByPrimaryKey(1L);
        Assert.assertEquals(1L, group.getId().longValue());
    }

    /**
     * 使用注解方式
     *
     * @author kk.xie
     * @date 2019/11/20 14:41
     */
    @Test
    public void selectByNameAnnotations(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        GroupMapper mapper = sqlSession.getMapper(GroupMapper.class);
        Group group = mapper.selectGroupByName("A类规则");
        Assert.assertEquals(6L, group.getId().longValue());
        sqlSession.close();
    }

    /**
     * typeHandler 查询转化测试
     *
     * @author kk.xie
     * @date 2019/11/20 17:09
     */
    @Test
    public void selectKkTypeHandlerNameById(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        GroupMapper mapper = sqlSession.getMapper(GroupMapper.class);
        Group group = mapper.selectTypeHandlerNameById(6L);
        Assert.assertEquals("A类规则-kk", group.getName());
        sqlSession.close();
    }

    /**
     * typeHandler 更新转化测试
     *
     * @author kk.xie
     * @date 2019/11/20 17:09
     */
    @Test
    public void updateKkTypeHandlerNameById(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        GroupMapper mapper = sqlSession.getMapper(GroupMapper.class);
        int i =mapper.updateNameById("TestName", 1L);
        Assert.assertEquals(1, i);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 拦截所有的更新，添加修改时间和修改人
     *
     * @author kk.xie
     * @date 2019/11/20 17:10
     */
    @Test
    public void updateByKkUpdatePlugin(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        GroupMapper mapper = sqlSession.getMapper(GroupMapper.class);
        int i = mapper.updateNameByPluginById("TestName", 1L, null, null);
        Assert.assertEquals(1, i);
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * github分页插件使用
     *
     * @author kk.xie
     * @date 2019/11/21 11:51
     */
    @Test
    public void selectPageByGithubPageHelper(){
        PageHelper.startPage(1,2);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        GroupMapper mapper = sqlSession.getMapper(GroupMapper.class);
        List<Group> groupList = mapper.selectAll();

        PageInfo<Group> pageInfo = new PageInfo<Group>(groupList);
        Assert.assertEquals(2, pageInfo.getSize());
        Assert.assertEquals(5, pageInfo.getTotal());
    }

    /**
     * 自定义分页插件使用，使用前先在mybatis-config.xml中注册
     *
     * @author kk.xie
     * @date 2019/11/21 11:51
     */
    @Test
    public void selectPageByKkPageHelper(){
        KkPageHelper.startPage(1,1);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        GroupMapper mapper = sqlSession.getMapper(GroupMapper.class);
        List<Group> groupList = mapper.selectAll();
        Assert.assertEquals(1, groupList.size());

        // 此处要重新开一个session,否则会做走一级缓存
        sqlSession = sqlSessionFactory.openSession();
        mapper = sqlSession.getMapper(GroupMapper.class);
        KkPageHelper.startPage(1,3);
        groupList = mapper.selectAll();
        Assert.assertEquals(3, groupList.size());
    }
}
