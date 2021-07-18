package com.kk.mybatis.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kk.mybatis.config.MybatisConfiguration;
import com.kk.mybatis.config.MybatisConfigurationXml;
import com.kk.mybatis.mapper.GroupMapper;
import com.kk.mybatis.model.Group;
import com.kk.mybatis.model.GroupRuler;
import com.kk.mybatis.page.KkPageHelper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
     * 依赖 com.kk.mybatis.plugins.KkUpdatePlugin
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
     * 核心： com.github.pagehelper.PageInterceptor  plugins
     * 使用ThreadLocal实现
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
     * 依赖： com.kk.mybatis.page.KkPageInterceptor plugins
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

    /*
    * 缓存测试
    * 1. 一级缓存
    * 2. 二级缓存
    * 3. 关联查询值懒加载
    * */


    /**
     * 一级缓存测试，session级别的缓存
     * 同一个session 中查询两次 则不会重新查询，不同session则会重新查询
     *
     * @param
     * @return void
     * @throws
     * @author kk.xie
     * @date 2019/11/26 19:07
     */
    @Test
    public void selectOneCache(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        GroupMapper mapper = sqlSession.getMapper(GroupMapper.class);
        // session-1 首次查询
        mapper.selectByPrimaryKey(1L);
        // session-1 再次查询
        mapper.selectByPrimaryKey(1L);
    }

    /**
     * 二级缓存测试，Mapper级别的测试
     * 1、首先在mapper.xml中开启二级缓存
     * 2、不同的session,相同的查询仅会查询一次
     *
     * @param
     * @return void
     * @throws
     * @author kk.xie
     * @date 2019/11/26 19:10
     */
    @Test
    public void selectTwoCache(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        GroupMapper mapper = sqlSession.getMapper(GroupMapper.class);
        // session-1 首次查询
        mapper.selectByPrimaryKey(1L);
        //这个close必须要加，不然缓存存不进去。
        sqlSession.close();

        // session-2 首次查询
        sqlSession = sqlSessionFactory.openSession();
        mapper = sqlSession.getMapper(GroupMapper.class);
        mapper.selectByPrimaryKey(1L);
    }

    /**
     * mybatis 联合查询，一次查询并返回所有值
     *
     * @param
     * @return void
     * @throws
     * @author kk.xie
     * @date 2019/11/26 19:28
     */
    @Test
    public void selectGroupRulerJoin(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        GroupMapper mapper = sqlSession.getMapper(GroupMapper.class);
        GroupRuler groupRuler = mapper.selectGroupRulerJoin(4L);
        System.out.println(groupRuler);
    }

    /**
     * mybatis 嵌套查询，先查询主数据，再逐一查询主数据的关系数据
     * 其中有懒加载
     *
     * @param
     * @return void
     * @throws
     * @author kk.xie
     * @date 2019/11/26 19:29
     */
    @Test
    public void selectGroupRulerOne2More(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        GroupMapper mapper = sqlSession.getMapper(GroupMapper.class);
        GroupRuler groupRuler = mapper.selectGroupRulerOne2More(4L);
        System.out.println(groupRuler);
    }

    @Test
    public void show(){
        System.out.println("------");
    }
}
