package com.yuntian.poeticlife.service;

import com.yuntian.poeticlife.core.Service;
import com.yuntian.poeticlife.model.dto.OperaterDTO;
import com.yuntian.poeticlife.model.entity.BackendOperater;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.model.vo.MenuTreeVO;
import com.yuntian.poeticlife.model.vo.PageInfoVo;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/02/23.
 */
public interface BackendOperaterService extends Service<BackendOperater> {


    BackendOperater findOperaterByAccount(String accountName);

    Long getRoleId(Long operaterId);

    String getRole(Long operaterId);

    List<Menu> getNavMenuListByOperater(Long operaterId);


    List<MenuTreeVO> getMenuTreeVOListByOperater(Long operaterId);


    void isEnable(BackendOperater role);

    void isStop(BackendOperater role);

    public PageInfoVo<BackendOperater> queryRoleListByPage(OperaterDTO dto);
}
