package com.ximo.springbootblogmaster.service.impl;

import com.ximo.springbootblogmaster.domain.User;
import com.ximo.springbootblogmaster.repository.UserRepository;
import com.ximo.springbootblogmaster.service.UserService;
import com.ximo.springbootblogmaster.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

import static com.ximo.springbootblogmaster.constant.CommonConstant.LIKE;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description user服务.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void removeUsersInBatch(List<User> users) {
        userRepository.deleteInBatch(users);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    /**
     * getOne 不存在则直接抛出异常
     *
     * @param id 主键id
     * @return 用户信息
     */
    @Override
    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> listUsersByNameLike(String name, Pageable pageable) {
        return userRepository.findByNameLike(CommonUtil.formatLikeString(name), pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    /**
     * 加载用户
     *
     * @param username 用户名
     * @return 密码
     */
    @Override
    public List<User> listUsersByUsername(Collection<String> username) {
        return userRepository.findByUsernameIn(username);
    }

}
