package com.ximo.springbootblogmaster.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author 朱文赵
 * @date 2018/4/7
 * @description
 */
@Entity
@Data
public class User {

    /** 用户的唯一标识*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 映射为字段，值不能为空*/
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

    /** 登录时密码 */
    @NotEmpty(message = "密码不能为空")
    @Size(max=100)
    @Column(length = 100)
    private String password;

    /** 头像图片地址*/
    @Column(length = 200)
    private String avatar;

    public User(Long id) {
        this.id = id;
    }
}
