package com.ximo.springbootblogmaster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 分类.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
 
	public Catalog(User user, String name) {
		this.name = name;
		this.user = user;
	}

}
