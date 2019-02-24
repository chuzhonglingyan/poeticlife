package com.yuntian.poeticlife;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: yuntian
 * @Date: 2019/2/23 0023 19:55
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DubboProviderApplication.class)
public class BaseTest {

    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);


}
