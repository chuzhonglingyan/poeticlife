package com.yuntian.poeticlife.model.dto;

/**
 * @Auther: yuntian
 * @Date: 2018/8/29 20:11
 * @Description:
 */
public class UserPageDTO {

    private int pageNum=0;

    private int pageSize=10;


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
