package com.yuntian.poeticlife.model.vo;

import com.yuntian.poeticlife.model.entity.Menu;

import java.util.List;

/**
 * @Auther: yuntian
 * @Date: 2019/2/27 0027 22:00
 * @Description:
 */
public class MenuTreeVO extends Menu {

    private List<MenuTreeVO> childList;

    public List<MenuTreeVO> getChildList() {
        return childList;
    }

    public void setChildList(List<MenuTreeVO> childList) {
        this.childList = childList;
    }
}
