package com.ximo.springbootblogmaster.repository;

import com.ximo.springbootblogmaster.domain.Blog;
import com.ximo.springbootblogmaster.domain.User;
import com.ximo.springbootblogmaster.dto.BlogDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotEquals;

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
        List<Object[]> blogs = blogRepository.listUserVotedAndCommentedBlog(2L);
        System.out.println(blogs.toString());
        for (Object[] object : blogs) {
            System.out.println(object[0]);
            System.out.println(object[1]);
            System.out.println(object[2]);
        }
    }

    @Test
    public void testFindByUser() {
        User user = new User();
        user.setId(2L);
        List<Blog> result = blogRepository.findByUser(user);
        System.out.println(result);
        assertNotEquals(0, result.size());
    }

    @Test
    public void testFindByUserId() {
        User user = new User();
        user.setId(2L);
        List<Blog> result = blogRepository.findByUserCustomer(user);
        System.out.println(result);
    }
}