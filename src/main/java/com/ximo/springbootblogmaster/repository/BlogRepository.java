package com.ximo.springbootblogmaster.repository;

import com.ximo.springbootblogmaster.domain.Blog;
import com.ximo.springbootblogmaster.domain.Catalog;
import com.ximo.springbootblogmaster.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 博客仓库.
 */
public interface BlogRepository extends JpaRepository<Blog, Long> {

    /**
     * 根据用户名分页查询用户列表（最新）
     * 由 findByUserAndTitleLikeOrTagsLikeOrderByCreateTimeDesc 代替，可以根据标签进行查询
     *
     * @param user
     * @param title
     * @param pageable
     * @return 分页blog
     * @deprecated
     */
    @Deprecated
    Page<Blog> findByUserAndTitleLikeOrderByCreateTimeDesc(User user, String title, Pageable pageable);

    /**
     * 根据用户名,博客标题分页查询博客列表
     *
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);

    /**
     * 根据用户名，博客标题，标签分页查询博客列表 （时间逆序）
     * user 在这里体现的是user_id
     *
     * @param title
     * @param user
     * @param tags
     * @param user2
     * @param pageable
     * @return
     */
    Page<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(String title, User user, String tags, User user2, Pageable pageable);

    /**
     * 根据分类分页查询博客列表
     *
     * @param catalog
     * @param pageable
     * @return
     */
    Page<Blog> findByCatalog(Catalog catalog, Pageable pageable);

    /**
     * 查询用户已经点赞和评论的blog
     *
     * @param userId 用户id
     * @return
     */
    @Query(value = "SELECT a.* FROM blog a JOIN blog_comment b ON a.id = b.blog_id " +
            "JOIN comment c ON b.comment_id = c.id join blog_vote d on a.id = d.blog_id " +
            "JOIN vote e on d.vote_id = e.id and e.user_id = :userId",
            nativeQuery = true)
    List<Blog> listUserVotedAndCommentedBlog(@Param("userId") Long userId);
}
