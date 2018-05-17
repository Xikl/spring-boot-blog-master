package com.ximo.springbootblogmaster.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author 朱文赵
 * @date 2018/5/17
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServerUrlConfigTest {

    @Autowired
    private FileServerUrlConfig fileServerUrlConfig;

    @Test
    public void getUrl() {
        System.out.println(fileServerUrlConfig.getUrl());
    }
}