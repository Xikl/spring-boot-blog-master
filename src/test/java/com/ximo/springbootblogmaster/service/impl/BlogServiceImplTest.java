package com.ximo.springbootblogmaster.service.impl;

import com.ximo.springbootblogmaster.domain.Blog;
import com.ximo.springbootblogmaster.service.BlogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 朱文赵
 * @date 2018/5/30
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogServiceImplTest {

    @Autowired
    private BlogService blogService;

    @Test
    public void listUserVotedAndCommentedBlog() {
        List<Blog> blogs = blogService.listUserVotedAndCommentedBlog(2L);
        System.out.println(blogs);
    }
}