package com.ximo.springbootblogmaster.service.impl;

import com.ximo.springbootblogmaster.domain.*;
import com.ximo.springbootblogmaster.domain.es.EsBlog;
import com.ximo.springbootblogmaster.enums.ResultEnums;
import com.ximo.springbootblogmaster.exception.BlogException;
import com.ximo.springbootblogmaster.repository.BlogRepository;
import com.ximo.springbootblogmaster.service.BlogService;
import com.ximo.springbootblogmaster.service.EsBlogService;
import com.ximo.springbootblogmaster.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 博客service.
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private EsBlogService esBlogService;

    /**
     * 保存
     *
     * @param blog
     * @return
     */
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        boolean isNew = (blog.getId() == null);
        EsBlog esBlog;

        Blog returnBlog = blogRepository.save(blog);

        if (isNew) {
            esBlog = new EsBlog(returnBlog);
        } else {
            esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
            esBlog.update(returnBlog);
        }

        esBlogService.updateEsBlog(esBlog);
        return returnBlog;
    }

    /**
     * @param id
     */
    @Transactional(rollbackOn = Exception.class)
    @Override
    public void removeBlog(Long id) {
        blogRepository.deleteById(id);
        EsBlog esblog = esBlogService.getEsBlogByBlogId(id);
        esBlogService.removeEsBlog(esblog.getId());
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    /**
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable) {
        // 模糊查询
        title = CommonUtil.formatLikeString(title);
        //Page<Blog> blogs = blogRepository.findByUserAndTitleLikeOrderByCreateTimeDesc(user, title, pageable);
        String tags = title;
        return blogRepository.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(title, user, tags, user, pageable);
    }

    @Override
    public Page<Blog> listBlogsByTitleVoteAndSort(User user, String title, Pageable pageable) {
        // 模糊查询
        title = CommonUtil.formatLikeString(title);
        return blogRepository.findByUserAndTitleLike(user, title, pageable);
    }

    @Override
    public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
        return blogRepository.findByCatalog(catalog, pageable);
    }

    @Override
    public void readingIncrease(Long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new BlogException(ResultEnums.RESOURCE_NOT_FOUND));
        //todo  加锁 改为原子变量
        AtomicInteger integer = new AtomicInteger(blog.getCommentSize());
        blog.setReadSize(integer.incrementAndGet());
        this.saveBlog(blog);
    }

    @Override
    public Blog createComment(Long blogId, String commentContent) {
        Blog originalBlog = blogRepository.findById(blogId).orElseThrow(() -> new BlogException(ResultEnums.RESOURCE_NOT_FOUND));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = new Comment(user, commentContent);
        originalBlog.addComment(comment);
        return this.saveBlog(originalBlog);
    }

    @Override
    public void removeComment(Long blogId, Long commentId) {
        Blog originalBlog = blogRepository.findById(blogId).orElseThrow(() -> new BlogException(ResultEnums.RESOURCE_NOT_FOUND));
        originalBlog.removeComment(commentId);
        this.saveBlog(originalBlog);
    }

    @Override
    public Blog createVote(Long blogId) {
        Blog originalBlog = blogRepository.findById(blogId).orElseThrow(() -> new BlogException(ResultEnums.RESOURCE_NOT_FOUND));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vote vote = new Vote(user);
        boolean isExist = originalBlog.addVote(vote);
        if (isExist) {
            throw new IllegalArgumentException("该用户已经点过赞了");
        }
        return this.saveBlog(originalBlog);
    }

    @Override
    public void removeVote(Long blogId, Long voteId) {
        Blog originalBlog = blogRepository.findById(blogId).orElseThrow(() -> new BlogException(ResultEnums.RESOURCE_NOT_FOUND));
        originalBlog.removeVote(voteId);
        this.saveBlog(originalBlog);
    }
}
