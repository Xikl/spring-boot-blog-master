package com.ximo.springbootblogmaster.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 朱文赵
 * @date 2018/4/6
 * @description 标签对象.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagVO implements Serializable {

	private static final long serialVersionUID = 2022091866040253222L;

	/** 标签名字*/
	private String name;

	private Long count;

}
