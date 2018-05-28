package com.ximo.springbootblogmaster.service;

import com.ximo.springbootblogmaster.domain.User;
import com.ximo.springbootblogmaster.domain.Vote;

import java.util.List;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description vote服务接口.
 */
public interface VoteService {
    /**
     * 根据id获取 Vote
     *
     * @param id 点赞id
     * @return 获得vote
     */
    Vote getVoteById(Long id);

    /**
     * 删除Vote
     *
     * @param id 点赞id
     */
    void removeVote(Long id);

    /**
     * 通过用户查找他的点赞列表
     *
     * @param user 用户
     * @return 点赞列表
     */
    List<Vote> findByUser(User user);
}
