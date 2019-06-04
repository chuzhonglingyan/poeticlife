package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.dao.OpreaterImageMapper;
import com.yuntian.poeticlife.model.entity.OpreaterImage;
import com.yuntian.poeticlife.service.OpreaterImageService;
import com.yuntian.poeticlife.core.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/03/31.
 */
@Service("opreaterImageService")
public class OpreaterImageServiceImpl extends AbstractService<OpreaterImage> implements OpreaterImageService {
    @Resource
    private OpreaterImageMapper opreaterImageMapper;

}
