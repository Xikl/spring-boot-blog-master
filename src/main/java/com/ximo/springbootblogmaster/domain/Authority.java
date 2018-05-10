package com.ximo.springbootblogmaster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 权限控制器.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 1873448714012612535L;

	/** 权限id */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 映射为字段，值不能为空 */
	@Column(nullable = false)
	private String name;

	/** 方法实现 接口*/
	@Override
	public String getAuthority() {
		return name;
	}

}
