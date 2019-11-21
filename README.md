# mybatis-demo
## mybatis核心点
### Environment
基本环境配置
### TypeHandler
数据类型的转换，如VARCHAR与String的转化，自定义数据类型的转化
### Plugins
插件，接口Interceptor，如github.PageHelper，自定义分页插件，自定义更新插件
## Mapper核心
### namespace
关联接口方法，类似package
### resultMap,resultType
### 动态sql
### 缓存
#### 一级缓存
SqlSession几倍
#### 二级缓存
Mapper级别，不推荐
### 分页
#### 逻辑分页
mybatis自带分页，基于ResultSet进行的逻辑分页
#### 物理分页
limit 0,10
### Batch操作
#### for
一次一个会话，一次仅提交一条，效率最低
#### 拼sql
一次一个会话，拼接一个长sql语句，如批量插入，效率高
#### 一批提交多条
一次一个会话，一次提交多条语句，效率居中
### 联合查询
#### 嵌套结果
一次性全查询出来
#### 嵌套查询
分批查询<br>
N+1问题<br>
使用懒加载