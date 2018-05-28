package com.ximo.springbootblogmaster.repository;

import com.ximo.springbootblogmaster.domain.User;
import com.ximo.springbootblogmaster.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 点赞仓库.
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {

    /**
     * 通过用户查找点赞列表
     *
     * @param user 用户
     * @return 点赞列表
     */
    List<Vote> findByUser(User user);

}
