package com.yuntian.poeticlife.service;

import com.yuntian.poeticlife.core.Service;
import com.yuntian.poeticlife.model.dto.BackendOperaterDTO;
import com.yuntian.poeticlife.model.entity.BackendOperater;
import com.yuntian.poeticlife.model.entity.Menu;
import com.yuntian.poeticlife.model.vo.MenuTreeVO;
import com.yuntian.poeticlife.model.vo.PageInfoVo;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/02/23.
 */
public interface BackendOperaterService extends Service<BackendOperaterDTO, BackendOperater> {


    BackendOperater findOperaterByAccount(String accountName);

    void isEnable(BackendOperater role);

    void isStop(BackendOperater role);


    PageInfoVo<BackendOperater> queryListByPage(BackendOperaterDTO dto);

    Long getRoleId(Long operaterId);

    String getRole(Long operaterId);

    List<Menu> getNavMenuListByOperater(Long operaterId);


    List<MenuTreeVO> getMenuTreeVOListByOperater(Long operaterId);


}
