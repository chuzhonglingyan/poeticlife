package com.yuntian.poeticlife.model.vo;

import com.yuntian.poeticlife.model.entity.Role;

import java.io.Serializable;

public class RoleVO extends Role implements Serializable {


    private static final long serialVersionUID = -1474181370135140579L;

    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}