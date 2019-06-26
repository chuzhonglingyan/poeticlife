package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.exception.BusinessException;
import com.yuntian.poeticlife.dao.ArticleMapper;
import com.yuntian.poeticlife.service.ArticleService;
import com.yuntian.poeticlife.model.dto.ArticleDTO;
import com.yuntian.poeticlife.model.entity.Article;
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
 * Created by CodeGenerator on 2019/06/26.
 */
@Service("articleService")
public class ArticleServiceImpl extends AbstractService<ArticleDTO,Article> implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;


    @Override
    public PageInfoVo<Article> queryListByPage(ArticleDTO dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        Condition condition = new Condition(Article.class);
        condition.orderBy("updateTime").desc();
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("isDelete", 0);
        List<Article> list = findByCondition(condition);
        return new PageInfoVo<>(new PageInfo<>(list));
    }

    @Override
    public void saveByDTO(Article dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        save(dto);
    }

    @Override
    public void updateByDTO(Article dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(), "文章id不能为空");
        int count=update(dto);
        if(count!=1){
           BusinessException.throwMessage("更新该文章失败,请刷新页面");
        }
    }

    @Override
    public void deleteByDTO(Article dto) {
        AssertUtil.isNotNull(dto, "参数不能为空");
        AssertUtil.isNotNull(dto.getId(),"文章id不能为空");
        dto.setIsDelete((byte) 1);
        int count=update(dto);
        if(count!=1){
           BusinessException.throwMessage("删除该文章失败,请刷新页面");
        }
    }


    @Override
    public Article findById(Long id) {
        Condition condition = new Condition(Article.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("id", id);
        criteria.andEqualTo("isDelete", 0);
        List<Article> list = findByCondition(condition);
        if (CollectionUtils.isNotEmpty(list)) {
           return list.get(0);
        }
        return null;
    }



}
