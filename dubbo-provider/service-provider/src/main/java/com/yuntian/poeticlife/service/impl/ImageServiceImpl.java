package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.exception.BusinessException;
import com.yuntian.poeticlife.dao.ImageMapper;
import com.yuntian.poeticlife.service.ImageService;
import com.yuntian.poeticlife.model.dto.ImageDTO;
import com.yuntian.poeticlife.model.vo.ImageVO;
import com.yuntian.poeticlife.model.entity.Image;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import  com.yuntian.poeticlife.util.AssertUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections.CollectionUtils;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import javax.annotation.Resource;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import java.util.List;

/**
 * Created by CodeGenerator on 2019/06/17.
 */
@Service("imageService")
public class ImageServiceImpl extends AbstractService<ImageDTO,ImageVO> implements ImageService {

    @Resource
    private ImageMapper imageMapper;


    @Override
    public PageInfoVo<ImageVO> queryListByPage(ImageDTO dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Condition condition = new Condition(Image.class);
        condition.orderBy("updateTime").desc();
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        List<ImageVO> list = findByCondition(condition);
        return new PageInfoVo<>(new PageInfo<>(list));
    }

    @Override
    public void saveByDTO(ImageVO dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        save(dto);
    }

    @Override
    public void updateByDTO(ImageVO dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(), "图片id不能为空");
        int count=update(dto);
        if(count!=1){
           BusinessException.throwMessage("更新该图片失败,请刷新页面");
        }
    }

    @Override
    public void deleteByDTO(ImageVO dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(),"图片id不能为空");
        dto.setIsDelete((byte) 1);
        int count=update(dto);
        if(count!=1){
           BusinessException.throwMessage("删除该图片失败,请刷新页面");
        }
    }


    @Override
    public ImageVO findById(Long id) {
        Condition condition = new Condition(Image.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("id", id);
        criteria.andEqualTo("isDelete", 0);
        List<ImageVO> list = findByCondition(condition);
        if (CollectionUtils.isNotEmpty(list)) {
           return list.get(0);
        }
        return null;
    }



}
