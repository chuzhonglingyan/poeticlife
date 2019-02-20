package com.yuntian.poeticlife.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Auther: yuntian
 * @Date: 2018/8/20 22:30
 * @Description:
 */
public class PageInfoVo<T> {

    private int pageNum;
    private int pageSize;
    private long total;
    private List<T> list;

    @JSONField(serialize = false) //忽略该字段显示
    private PageInfo pageInfo;

    public PageInfoVo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public int getPageNum() {
        return pageInfo.getPageNum();
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageInfo.getPageSize();
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return pageInfo.getList();
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return pageInfo.getTotal();
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
