package com.ximo.springbootblogmaster.repository;

import com.ximo.springbootblogmaster.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 朱文赵
 * @date 2018/4/7
 * @description
 */
public interface UserRepository extends JpaRepository<User, Long>{

    /** 通过姓名查找 */
    User findByUsername(String username);

    /**
     * 通过模糊查询查询user
     *
     * @param username
     * @param pageable
     * @return
     */
    Page<User> findByUsernameLike(String username, Pageable pageable);

}
