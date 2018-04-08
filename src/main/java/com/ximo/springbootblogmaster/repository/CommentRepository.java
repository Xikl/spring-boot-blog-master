package com.ximo.springbootblogmaster.repository;

import com.ximo.springbootblogmaster.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 评论仓库.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
