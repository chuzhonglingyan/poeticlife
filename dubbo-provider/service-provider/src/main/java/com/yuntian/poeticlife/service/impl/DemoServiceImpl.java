package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.service.DemoService;

import org.springframework.stereotype.Service;

@Service("demoService")
public class DemoServiceImpl implements DemoService {

    public String sayHello(String name) {
        return "Hello, " + name + " (from Spring Boot)";
    }

}
