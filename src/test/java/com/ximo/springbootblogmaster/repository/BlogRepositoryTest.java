package com.ximo.springbootblogmaster.repository;

import com.ximo.springbootblogmaster.domain.Blog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 朱文赵
 * @date 2018/5/29
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogRepositoryTest {

    @Autowired
    private BlogRepository blogRepository;


    @Test
    public void listUserVotedAndCommentedBlog() {
        List<Blog> blogs = blogRepository.listUserVotedAndCommentedBlog(2L);
        System.out.println(blogs);
    }
}