package com.yuntian.poeticlife;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.alibaba.fastjson.JSON;
import com.yuntian.poeticlife.cache.RedisUtil;
import com.yuntian.poeticlife.dao.BackendOperaterMapper;
import com.yuntian.poeticlife.model.entity.BackendOperater;
import com.yuntian.poeticlife.service.BackendOperaterService;

import org.junit.Test;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by CodeGenerator on 2019/02/21.
 */
@Slf4j
public class BackendOperaterServiceTest extends BaseTest {

    @Resource
    private BackendOperaterService backendOperaterService;
    @Resource
    private BackendOperaterMapper backendOperaterMapper;

    @Resource
    private RedisUtil redisUtil;


    @Test
    public void  test(){
        List<BackendOperater> list=backendOperaterService.findAll();

        redisUtil.set("backendOperater",list.get(0));


        redisUtil.set("backendOperater",list.get(0));

        BackendOperater backendOperater=redisUtil.get("backendOperater");
        log.error(JSON.toJSONString(backendOperater));


        log.error(JSON.toJSONString(list));
        log.error(JSON.toJSONString(DruidStatManagerFacade.getInstance().getDataSourceStatDataList()));

    }

    @Test
    public void  updateByPrimaryKey(){
        BackendOperater m=new BackendOperater();
        m.setId(5L);
        m.setUpdateId(1L);
        m.setAccountName("ceshi04");
        m.setUserName("哈哈");
        m.setEmail("944610721@qq.com");
        int result= backendOperaterMapper.updateByPrimaryKey(m);
        System.out.println(result);
    }

    @Test
    public void  updateByPrimaryKeySelective(){
        BackendOperater m=new BackendOperater();
        m.setId(5L);
        m.setUpdateId(1L);
        m.setUserName("哈哈");
        m.setEmail("944610721@qq.com");
        int result= backendOperaterMapper.updateByPrimaryKeySelective(m);
        System.out.println(result);
    }
}
