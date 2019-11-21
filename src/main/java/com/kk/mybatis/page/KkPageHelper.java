package com.kk.mybatis.page;

/**
 * kk 分页帮助类
 *
 * @Author kk.xie
 * @Date 2019/11/21 11:09
 * @Version 1.0
 **/
public class KkPageHelper {
    private static final ThreadLocal<KkPage> KK_PAGE = new ThreadLocal<KkPage>();

    public static KkPage startPage(int pageNum, int pageSize){
        KkPage kkPage = new KkPage(pageNum, pageSize);
        KK_PAGE.set(kkPage);
        return kkPage;
    }

    public String getPageSql(String sql) {
        KkPage page = KK_PAGE.get();
        StringBuilder sqlBuilder = new StringBuilder(sql.length() + 14);
        sqlBuilder.append(sql);

        sqlBuilder.append(" LIMIT " + page.getPageNum() + ", " + page.getPageSize() + " ");
        KK_PAGE.remove();
        return sqlBuilder.toString();
    }
}
