package com.ximo.springbootblogmaster.service;


import com.ximo.springbootblogmaster.domain.Authority;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description Authority服务接口.
 */
public interface AuthorityService {


	/**
	 * 根据权限id查询权限
	 *
	 * @param id 权限id
	 * @return 权限
	 */
	Authority getAuthorityById(Long id);
}
