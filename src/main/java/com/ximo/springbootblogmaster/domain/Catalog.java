package com.ximo.springbootblogmaster.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 分类.
 */
@Entity
@Data
public class Catalog implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 用户的唯一标识*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "名称不能为空")
	@Size(min=2, max=30)
	@Column(nullable = false)
	private String name;
 
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
 
	protected Catalog() {
	}
	
	public Catalog(User user, String name) {
		this.name = name;
		this.user = user;
	}

}
