package com.yuntian.poeticlife.service.impl;


import com.yuntian.poeticlife.core.AbstractService;
import com.yuntian.poeticlife.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @ Author     ：guangleilei.
 * @ Date       ：Created in 16:05 2018/12/20
 * @ Description：${description}
 */
@Service("helloService")
public class HelloServiceImpl extends AbstractService implements HelloService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void say(String msg) {
        logger.info(msg);
    }

}
