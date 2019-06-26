package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.exception.BusinessException;
import com.yuntian.poeticlife.dao.DictMapper;
import com.yuntian.poeticlife.service.DictService;
import com.yuntian.poeticlife.model.dto.DictDTO;
import com.yuntian.poeticlife.model.entity.Dict;
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
@Service("dictService")
public class DictServiceImpl extends AbstractService<DictDTO,Dict> implements DictService {

    @Resource
    private DictMapper dictMapper;


    @Override
    public PageInfoVo<Dict> queryListByPage(DictDTO dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Condition condition = new Condition(Dict.class);
        condition.orderBy("updateTime").desc();
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        List<Dict> list = findByCondition(condition);
        return new PageInfoVo<>(new PageInfo<>(list));
    }

    @Override
    public void saveByDTO(Dict dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        save(dto);
    }

    @Override
    public void updateByDTO(Dict dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(), "字典id不能为空");
        int count=update(dto);
        if(count!=1){
           BusinessException.throwMessage("更新该字典失败,请刷新页面");
        }
    }

    @Override
    public void deleteByDTO(Dict dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(),"字典id不能为空");
        dto.setIsDelete((byte) 1);
        int count=update(dto);
        if(count!=1){
           BusinessException.throwMessage("删除该字典失败,请刷新页面");
        }
    }


    @Override
    public Dict findById(Long id) {
        Condition condition = new Condition(Dict.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("id", id);
        criteria.andEqualTo("isDelete", 0);
        List<Dict> list = findByCondition(condition);
        if (CollectionUtils.isNotEmpty(list)) {
           return list.get(0);
        }
        return null;
    }



}
