package com.yuntian.poeticlife.service;
import com.yuntian.poeticlife.model.entity.BackendOperater;
import com.yuntian.poeticlife.core.Service;


/**
 * Created by CodeGenerator on 2019/02/23.
 */
public interface BackendOperaterService extends Service<BackendOperater> {


    String getRole(Long userId);

}
