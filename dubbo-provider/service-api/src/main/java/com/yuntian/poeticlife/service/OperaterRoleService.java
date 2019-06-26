package com.yuntian.poeticlife.service;

import com.yuntian.poeticlife.core.Service;
import com.yuntian.poeticlife.model.dto.OperaterRoleDTO;
import com.yuntian.poeticlife.model.entity.OperaterRole;
import com.yuntian.poeticlife.model.entity.Role;
import com.yuntian.poeticlife.model.vo.RoleVO;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/02/26.
 */
public interface OperaterRoleService extends Service<OperaterRoleDTO, OperaterRole> {


    List<Long> getRoleIdListByOperaterId(Long operaterId);

    List<RoleVO> getRoleListByOperaterId(Long operaterId);

    List<Long> getOperaterIdListByRoleId(Long roleId);

    void saveRoleListByOperaterId(OperaterRoleDTO operaterRoleDTO);

    void deleteOperaterId(Long operaterId) ;


}
