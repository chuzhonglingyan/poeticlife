package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.dao.ImageMapper;
import com.yuntian.poeticlife.model.entity.Image;
import com.yuntian.poeticlife.service.ImageService;
import com.yuntian.poeticlife.core.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/06/06.
 */
@Service("imageService")
public class ImageServiceImpl extends AbstractService<Image> implements ImageService {
    @Resource
    private ImageMapper imageMapper;

}
