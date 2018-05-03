package com.ximo.springbootblogmaster.service.impl;

import com.ximo.springbootblogmaster.domain.Authority;
import com.ximo.springbootblogmaster.enums.ResultEnums;
import com.ximo.springbootblogmaster.exception.BlogException;
import com.ximo.springbootblogmaster.repository.AuthorityRepository;
import com.ximo.springbootblogmaster.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 权限service.
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    /**
     * 根据权限id查询权限
     *
     * @param id 权限id
     * @return 权限
     */
    @Override
    public Authority getAuthorityById(Long id) {
        return authorityRepository.findById(id).orElseThrow(() -> new BlogException(ResultEnums.AUTHORITY_NOT_FOUND));
    }

}
