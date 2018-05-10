package com.ximo.springbootblogmaster.service;

import com.ximo.springbootblogmaster.domain.Vote;

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
}
