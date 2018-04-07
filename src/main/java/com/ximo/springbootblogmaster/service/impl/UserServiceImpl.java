package com.ximo.springbootblogmaster.service.impl;

import com.ximo.springbootblogmaster.domain.User;
import com.ximo.springbootblogmaster.repository.UserRepository;
import com.ximo.springbootblogmaster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ximo.springbootblogmaster.constant.CommonConstant.LIKE;

/**
 * @author 朱文赵
 * @date 2018/4/7
 * @description 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getByUserId(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Page<User> listUserByNameLike(String name, Pageable pageable) {
        return userRepository.findByUsernameLike(LIKE.concat(name).concat(LIKE), pageable);
    }

    @Override
    public Page<User> listUserByNameContaining(String name, Pageable pageable) {
        return userRepository.findByUsernameContaining(name, pageable);
    }
}
