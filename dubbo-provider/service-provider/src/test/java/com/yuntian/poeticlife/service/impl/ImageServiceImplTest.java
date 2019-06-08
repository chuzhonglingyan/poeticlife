package com.yuntian.poeticlife.service.impl;

import com.yuntian.poeticlife.model.entity.Image;
import com.yuntian.poeticlife.service.ImageService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Auther: yuntian
 * @Date: 2019/6/7 0007 15:08
 * @Description:
 */
public class ImageServiceImplTest {

    @Resource
    private ImageService imageService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void save() {
        Image image=new Image();

        image.setImageModule((byte) 0);

        image.setImageName("测试图片01");
        image.setImageType((byte) 0);
        image.setImageSize("");
        image.setImageMd5("");
        image.setcreateId(0L);
        image.setupdateId(0L);
        imageService.save(image);
    }



    @Test
    public void deleteById() {
    }

    @Test
    public void deleteByIds() {
    }

    @Test
    public void deleteByIds1() {
    }

    @Test
    public void update() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void findBy() {
    }

    @Test
    public void findByIds() {
    }


    @Test
    public void findByCondition() {
    }

    @Test
    public void findAll() {
    }
}