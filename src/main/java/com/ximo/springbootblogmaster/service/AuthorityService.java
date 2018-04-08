package com.ximo.springbootblogmaster.service;


import com.ximo.springbootblogmaster.domain.Authority;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description Authority服务接口.
 */
public interface AuthorityService {


	/**
	 *
	 * @param id
	 * @return
	 */
	Authority getAuthorityById(Long id);
}
