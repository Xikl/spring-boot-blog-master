package com.ximo.springbootblogmaster.repository;

import com.ximo.springbootblogmaster.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 点赞仓库.
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {

}
