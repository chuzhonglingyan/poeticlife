package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.exception.BusinessException;
import com.yuntian.poeticlife.dao.ArticleMapper;
import com.yuntian.poeticlife.service.ArticleService;
import com.yuntian.poeticlife.model.dto.ArticleDTO;
import com.yuntian.poeticlife.model.vo.ArticleVO;
import com.yuntian.poeticlife.model.vo.PageInfoVo;
import  com.yuntian.poeticlife.util.AssertUtil;
import com.yuntian.poeticlife.util.CglibBeanCopierUtils;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import javax.annotation.Resource;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import java.util.List;

/**
 * Created by CodeGenerator on 2019/06/13.
 */
@Service("articleService")
public class ArticleServiceImpl extends AbstractService<ArticleDTO,ArticleVO> implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;


    @Override
    public PageInfoVo<ArticleVO> queryListByPage(ArticleDTO dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Condition condition = new Condition(ArticleVO.class);
        condition.orderBy("updateTime").desc();
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        List<ArticleVO> list = findByCondition(condition);
        return new PageInfoVo<>(new PageInfo<>(list));
    }

    @Override
    public void saveByDTO(ArticleDTO dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        ArticleVO vo=new ArticleVO();
        CglibBeanCopierUtils.copyProperties(dto,vo);
        save(vo);
    }

    @Override
    public void updateByDTO(ArticleDTO dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(), "文章id不能为空");
        ArticleVO vo=new ArticleVO();
        CglibBeanCopierUtils.copyProperties(dto,vo);
        int count=update(vo);
        if(count!=1){
           BusinessException.throwMessage("更新该文章失败,请刷新页面");
        }
    }

    @Override
    public void deleteByDTO(ArticleDTO dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(),"文章id不能为空");
        ArticleVO vo=new ArticleVO();
        CglibBeanCopierUtils.copyProperties(dto,vo);
        vo.setIsDelete((byte) 1);
        int count=update(vo);
        if(count!=1){
           BusinessException.throwMessage("删除该文章失败,请刷新页面");
        }
    }



}
