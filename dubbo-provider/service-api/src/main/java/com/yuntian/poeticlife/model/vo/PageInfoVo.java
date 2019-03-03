package com.yuntian.poeticlife.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: yuntian
 * @Date: 2018/8/20 22:30
 * @Description:
 */
public class PageInfoVo<T> implements Serializable {


    private static final long serialVersionUID = -2291321936784760449L;

    private int pageNum;
    private int pageSize;
    private long total;
    private List<T> rows;

    @JSONField(serialize = false) //忽略该字段显示
    private PageInfo<T> pageInfo;

    public PageInfoVo(PageInfo<T> pageInfo) {
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

    public List<T> getRows() {
        return pageInfo.getList();
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return pageInfo.getTotal();
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
