package com.yuntian.poeticlife.config;

import com.jagregory.shiro.freemarker.ShiroTags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import freemarker.template.TemplateModelException;

/**
 * @Auther: yuntian
 * @Date: 2019/6/8 0008 09:03
 * @Description:
 */
@Component
public class ShiroTagsFreeMarkerCfg {

    @Resource
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @PostConstruct
    public void setSharedVariable() throws TemplateModelException {
        freeMarkerConfigurer.getConfiguration().setSharedVariable("shiro", new ShiroTags());
    }
}
