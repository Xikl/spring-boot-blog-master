package com.ximo.springbootblogmaster.service.impl;

import com.ximo.springbootblogmaster.domain.Vote;
import com.ximo.springbootblogmaster.repository.VoteRepository;
import com.ximo.springbootblogmaster.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 点赞服务.
 */
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    /**
     * @param id
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void removeVote(Long id) {
        voteRepository.deleteById(id);
    }

    @Override
    public Vote getVoteById(Long id) {
        return voteRepository.findById(id).orElse(null);
    }

}
