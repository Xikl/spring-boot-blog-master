package com.ximo.springbootblogmaster.service.impl;

import com.ximo.springbootblogmaster.domain.Comment;
import com.ximo.springbootblogmaster.repository.CommentRepository;
import com.ximo.springbootblogmaster.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 评论服务.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    /**
     * @param id
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void removeComment(Long id) {
        commentRepository.deleteById(id);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

}
