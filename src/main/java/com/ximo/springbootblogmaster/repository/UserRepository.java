package com.ximo.springbootblogmaster.repository;

import com.ximo.springbootblogmaster.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 用户仓库.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名分页查询用户列表
     *
     * @param name 昵称
     * @param pageable pageAble对象
     * @return 分页
     */
    Page<User> findByNameLike(String name, Pageable pageable);

    /**
     * 根据名称查询
     *
     * @param username 用户名
     * @return user类
     */
    User findByUsername(String username);

    /**
     * 根据名称列表查询
     *
     * @param username 用户名
     * @return 用户列表
     */
    List<User> findByUsernameIn(Collection<String> username);
}
