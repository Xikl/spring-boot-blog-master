package com.ximo.springbootblogmaster.service.impl;

import com.ximo.springbootblogmaster.domain.User;
import com.ximo.springbootblogmaster.domain.Vote;
import com.ximo.springbootblogmaster.enums.ResultEnums;
import com.ximo.springbootblogmaster.exception.BlogException;
import com.ximo.springbootblogmaster.repository.VoteRepository;
import com.ximo.springbootblogmaster.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 点赞服务实现.
 */
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    /**
     * 删除Vote
     *
     * @param id 点赞id
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void removeVote(Long id) {
        voteRepository.deleteById(id);
    }

    /**
     * 根据id获取 Vote
     *
     * @param id 点赞id
     * @return 获得vote
     */
    @Override
    public Vote getVoteById(Long id) {
        return voteRepository.findById(id).orElseThrow(() -> new BlogException(ResultEnums.RESOURCE_NOT_FOUND));
    }

    @Override
    public List<Vote> findByUser(User user) {
        return voteRepository.findByUser(user);
    }
}
