package com.ximo.springbootblogmaster.service;

import com.ximo.springbootblogmaster.domain.Comment;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 评论服务接口.
 */
public interface CommentService {
    /**
     * 根据id获取 Comment
     *
     * @param id
     * @return
     */
    Comment getCommentById(Long id);

    /**
     * 删除评论
     *
     * @param id
     * @return
     */
    void removeComment(Long id);
}
