package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.dao.ImageMapper;
import com.yuntian.poeticlife.model.dto.ImageDTO;
import com.yuntian.poeticlife.model.entity.Article;
import com.yuntian.poeticlife.model.entity.Image;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import com.yuntian.poeticlife.service.ImageService;
import com.yuntian.poeticlife.core.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/06/06.
 */
@Service("imageService")
public class ImageServiceImpl extends AbstractService<ImageDTO,Image> implements ImageService {
    @Resource
    private ImageMapper imageMapper;


    @Override
    public void saveByDTO(ImageDTO dto) {

    }

    @Override
    public void deleteByDTO(ImageDTO dto) {

    }

    @Override
    public void updateByDTO(ImageDTO dto) {

    }

    @Override
    public void save(Image model) {

        super.save(model);
    }

    @Override
    public PageInfoVo<Image> queryListByPage(ImageDTO dto) {
        return null;
    }
}
