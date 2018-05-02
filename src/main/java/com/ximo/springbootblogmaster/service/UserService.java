package com.ximo.springbootblogmaster.service;

import java.util.Collection;
import java.util.List;

import com.ximo.springbootblogmaster.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 用户服务接口.
 */
public interface UserService {
    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    User saveUser(User user);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    void removeUser(Long id);

    /**
     * 删除列表里面的用户
     *
     * @param users
     * @return
     */
    void removeUsersInBatch(List<User> users);

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    User updateUser(User user);

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * 获取用户列表
     *
     * @return
     */
    List<User> listUsers();

    /**
     * 根据用户名进行分页模糊查询
     *
     * @param name
     * @param pageable
     * @return
     */
    Page<User> listUsersByNameLike(String name, Pageable pageable);

    /**
     * 更具名称列表查询
     *
     * @param username
     * @return
     */
    List<User> listUsersByUsername(Collection<String> username);
}
