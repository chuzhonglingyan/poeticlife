package com.yuntian.poeticlife.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@PropertySource({"classpath:dubbo/dubbo.properties"})
@ImportResource({"classpath:dubbo/consumer.xml"})
@Configuration
public class DubboConfig {
}
