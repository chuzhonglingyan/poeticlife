package com.yuntian.poeticlife.model.vo;

import com.yuntian.poeticlife.model.entity.Menu;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class MenuVO extends Menu implements Serializable {


    private static final long serialVersionUID = -2302614850186897388L;


    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}