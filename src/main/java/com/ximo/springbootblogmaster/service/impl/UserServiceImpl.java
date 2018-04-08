package com.ximo.springbootblogmaster.service.impl;

import com.ximo.springbootblogmaster.domain.User;
import com.ximo.springbootblogmaster.repository.UserRepository;
import com.ximo.springbootblogmaster.service.UserService;
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
        // 模糊查询
        name = "%" + name + "%";
        Page<User> users = userRepository.findByNameLike(name, pageable);
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> listUsersByUsernames(Collection<String> usernames) {
        return userRepository.findByUsernameIn(usernames);
    }

}
