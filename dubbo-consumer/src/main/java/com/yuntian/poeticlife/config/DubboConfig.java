package com.yuntian.poeticlife.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@PropertySource({"classpath:dubbo/consumer.xml"})
@ImportResource({"classpath:dubbo/dubbo.properties"})
@Configuration
public class DubboConfig {
}
