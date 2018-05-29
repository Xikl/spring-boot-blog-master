package com.ximo.springbootblogmaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 朱文赵
 * @date 2018/5/30
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO implements Serializable{

    private static final long serialVersionUID = -1023487621113001520L;

    /** 主键 博客的唯一标识*/
    private Long id;

    /** 博客标题 */
    private String title;

    /** 标签 */
    private String tags;


}
