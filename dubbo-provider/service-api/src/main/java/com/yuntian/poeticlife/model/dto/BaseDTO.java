package com.yuntian.poeticlife.model.dto;

import java.io.Serializable;

/**
 * @Auther: yuntian
 * @Date: 2019/2/23 0023 23:02
 * @Description:
 */
public class BaseDTO  implements Serializable {


    private static final long serialVersionUID = -327327727652596175L;


    private Long id;


    private Long createId;

    private Long updateId;

    private Integer isDelete=0;

    private Integer pageNum=1;

    private Integer pageSize=10;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getcreateId() {
        return createId;
    }

    public void setcreateId(Long createId) {
        this.createId = createId;
    }

    public Long getupdateId() {
        return updateId;
    }

    public void setupdateId(Long updateId) {
        this.updateId = updateId;
    }
}
