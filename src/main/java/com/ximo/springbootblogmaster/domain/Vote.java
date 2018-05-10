package com.ximo.springbootblogmaster.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 点赞类.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vote implements Serializable {

	private static final long serialVersionUID = 1766541296060837038L;

	/** 主键 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 一对一关联 */
	@OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	/** 不能为空 由数据库自己来管理这个时间*/
	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp createTime;

	public Vote(User user) {
		this.user = user;
	}
}
