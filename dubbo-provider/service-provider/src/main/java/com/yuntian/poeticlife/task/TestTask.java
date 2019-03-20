package com.yuntian.poeticlife.task;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: yuntian
 * @Date: 2019/3/17 0017 14:29
 * @Description:
 */
@Component("testTask")
@Slf4j
public class TestTask {


    public void  test(){
        log.error("测试定时器");
    }
}
