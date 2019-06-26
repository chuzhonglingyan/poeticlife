package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.exception.BusinessException;
import com.yuntian.poeticlife.dao.OpreaterImageMapper;
import com.yuntian.poeticlife.service.OpreaterImageService;
import com.yuntian.poeticlife.model.dto.OpreaterImageDTO;
import com.yuntian.poeticlife.model.entity.OpreaterImage;
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
@Service("opreaterImageService")
public class OpreaterImageServiceImpl extends AbstractService<OpreaterImageDTO,OpreaterImage> implements OpreaterImageService {

    @Resource
    private OpreaterImageMapper opreaterImageMapper;


    @Override
    public PageInfoVo<OpreaterImage> queryListByPage(OpreaterImageDTO dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Condition condition = new Condition(OpreaterImage.class);
        condition.orderBy("updateTime").desc();
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        List<OpreaterImage> list = findByCondition(condition);
        return new PageInfoVo<>(new PageInfo<>(list));
    }

    @Override
    public void saveByDTO(OpreaterImage dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        save(dto);
    }

    @Override
    public void updateByDTO(OpreaterImage dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(), "用户图片id不能为空");
        int count=update(dto);
        if(count!=1){
           BusinessException.throwMessage("更新该用户图片失败,请刷新页面");
        }
    }

    @Override
    public void deleteByDTO(OpreaterImage dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(),"用户图片id不能为空");
        dto.setIsDelete((byte) 1);
        int count=update(dto);
        if(count!=1){
           BusinessException.throwMessage("删除该用户图片失败,请刷新页面");
        }
    }


    @Override
    public OpreaterImage findById(Long id) {
        Condition condition = new Condition(OpreaterImage.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("id", id);
        criteria.andEqualTo("isDelete", 0);
        List<OpreaterImage> list = findByCondition(condition);
        if (CollectionUtils.isNotEmpty(list)) {
           return list.get(0);
        }
        return null;
    }



}
