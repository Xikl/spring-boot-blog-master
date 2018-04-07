package com.ximo.springbootblogmaster.service;

import com.ximo.springbootblogmaster.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author 朱文赵
 * @date 2018/4/7
 * @description 用户服务接口
 */
public interface UserService {

    /** 保存用户*/
    User saveOrUpdateUser(User user);

    /** 注册 */
    User registerUser(User user);

    /** 删除用户*/
    void removeUser(Long id);

    /** 查找用户*/
    User getByUserId(Long id);

    /** 根据用户名模糊查询 */
    Page<User> listUserByNameLike(String name, Pageable pageable);

    /** 根据用户名包含查询*/
    Page<User> listUserByNameContaining(String name, Pageable pageable);


}
