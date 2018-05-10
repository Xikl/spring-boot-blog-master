package com.ximo.springbootblogmaster.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 用户.
 */
@Entity
@Data
@NoArgsConstructor
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = -1564620897358354792L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 昵称*/
	@NotEmpty(message = "姓名不能为空")
	@Size(min=2, max=20)
	@Column(nullable = false, length = 20)
	private String name;

	/** 邮箱*/
	@NotEmpty(message = "邮箱不能为空")
	@Size(max=50)
	@Email(message= "邮箱格式不对" )
	@Column(nullable = false, length = 50, unique = true)
	private String email;

	/** 用户账号，用户登录时的唯一标识 */
	@NotEmpty(message = "账号不能为空")
	@Size(min=3, max=20)
	@Column(nullable = false, length = 20, unique = true)
	private String username;

	/** 密码*/
	@NotEmpty(message = "密码不能为空")
	@Size(max=100)
	@Column(length = 100)
	private String password;

	/** 头像图片地址 */
	@Column(length = 200)
	private String avatar;

	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private List<Authority> authorities;

	public User(String name, String email,String username,String password) {
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	/**
	 * 需将 List<Authority> 转成 List<SimpleGrantedAuthority>，否则前端拿不到角色列表名称
	 *
	 * @return List<SimpleGrantedAuthority>
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
				.collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setEncodePassword(String password) {
		PasswordEncoder  encoder = new BCryptPasswordEncoder();
		this.password = encoder.encode(password);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
