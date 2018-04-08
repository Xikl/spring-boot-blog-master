package com.ximo.springbootblogmaster.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * @author 朱文赵
 * @date 2018/4/6
 * @description 标签对象.
 */
@Data
public class TagVO implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Long count;
	
	public TagVO(String name, Long count) {
		this.name = name;
		this.count = count;
	}

}
