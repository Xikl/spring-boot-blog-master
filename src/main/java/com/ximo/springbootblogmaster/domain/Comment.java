package com.ximo.springbootblogmaster.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 评论类.
 */
@Entity
public class Comment implements Serializable {

	private static final long serialVersionUID = 7078252576987822850L;

	/**  用户的唯一标识 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "评论内容不能为空")
	@Size(min=2, max=500)
	@Column(nullable = false)
	private String content;

	/** 用户*/
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	/** 创建时间*/
	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp createTime;
 
	protected Comment() {
	}

	public Comment(User user, String content) {
		this.content = content;
		this.user = user;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
 
	public Timestamp getCreateTime() {
		return createTime;
	}
 
}
