package com.yuntian.poeticlife.core;


import com.yuntian.poeticlife.exception.BusinessException;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import tk.mybatis.mapper.entity.Condition;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected Mapper<T> mapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    public void save(T model) {
        mapper.insertSelective(model);
    }


    public void save(List<T> models) {
        mapper.insertList(models);
    }

    public void deleteById(Long id) {
        mapper.deleteByPrimaryKey(id);
    }

    public void deleteByIds(String ids) {
        mapper.deleteByIds(ids);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        deleteByIds(getIds(ids));
    }

    public void update(T model) {
        mapper.updateByPrimaryKeySelective(model);
    }

    public T findById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new BusinessException(ResultCode.INTERNAL_SERVER_ERROR.code(), e.getMessage());
        }
    }

    @Override
    public List<T> findByIds(List<Long> ids) {
        return findByIds(getIds(ids));
    }


    private String  getIds(List<Long> ids){
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            stringBuilder.append(ids.get(i));
            if (i<ids.size()-1){
                stringBuilder.append(",");
            }
        }
        return  stringBuilder.toString();
    }

    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }


    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    public List<T> findAll() {
        return mapper.selectAll();
    }
}
