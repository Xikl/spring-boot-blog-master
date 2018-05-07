package com.ximo.springbootblogmaster.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 权限控制器.
 */
@Entity
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 1873448714012612535L;

	/** 权限id */
	@Id
	@Getter @Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 映射为字段，值不能为空 */
	@Column(nullable = false)
	@Setter
	private String name;

	/** 方法实现 接口*/
	@Override
	public String getAuthority() {
		return name;
	}

}
