package com.ximo.springbootblogmaster.repository;

import com.ximo.springbootblogmaster.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 权限仓库.
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long>{
}
