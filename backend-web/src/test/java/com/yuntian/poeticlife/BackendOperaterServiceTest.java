package com.yuntian.poeticlife;

import com.alibaba.fastjson.JSON;
import com.yuntian.poeticlife.cache.RedisUtil;
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
    private RedisUtil redisUtil;


    @Test
    public void  test(){
        List<BackendOperater> list=backendOperaterService.findAll();

        redisUtil.set("backendOperater",list.get(0));


        redisUtil.set("backendOperater",list.get(0));

        BackendOperater backendOperater=redisUtil.get("backendOperater");
        log.error(JSON.toJSONString(backendOperater));


        log.error(JSON.toJSONString(list));
    }

    @Test
    public void  test2(){
        log.error(backendOperaterService.getRole(Long.valueOf(1)));
    }

}
