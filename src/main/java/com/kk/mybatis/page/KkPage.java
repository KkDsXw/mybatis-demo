package com.kk.mybatis.page;

/**
 * kk 分页
 *
 * @Author kk.xie
 * @Date 2019/11/21 11:11
 * @Version 1.0
 **/
public class KkPage {
    private int pageNum;

    private int pageSize;

    public KkPage() {
    }

    public KkPage(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
