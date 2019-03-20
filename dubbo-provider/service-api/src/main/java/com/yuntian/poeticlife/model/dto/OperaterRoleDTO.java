package com.yuntian.poeticlife.model.dto;


import java.io.Serializable;
import java.util.List;

public class OperaterRoleDTO extends BaseDTO implements Serializable {


    private static final long serialVersionUID = 6700164928409280860L;

    private Long operaterId;
    private List<Long> roleList;

    public Long getOperaterId() {
        return operaterId;
    }

    public void setOperaterId(Long operaterId) {
        this.operaterId = operaterId;
    }

    public List<Long> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Long> roleList) {
        this.roleList = roleList;
    }
}