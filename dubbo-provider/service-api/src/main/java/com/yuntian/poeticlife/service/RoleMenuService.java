package com.yuntian.poeticlife.service;

import com.yuntian.poeticlife.core.Service;
import com.yuntian.poeticlife.model.dto.RoleMenuDTO;
import com.yuntian.poeticlife.model.entity.RoleMenu;
import com.yuntian.poeticlife.model.vo.MenuVO;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/02/26.
 */
public interface RoleMenuService extends Service<RoleMenuDTO,RoleMenu> {


    List<Long> getMenuIdListByRoleId(Long roleId);


    List<MenuVO> getMenuListByRoleId(Long roleId);


    List<RoleMenu> findByRoleId(Long roleId);

   void  deleteByRoleId(Long roleId);


   void saveMenuListByRoleId(RoleMenuDTO roleMenuDTO);
}
