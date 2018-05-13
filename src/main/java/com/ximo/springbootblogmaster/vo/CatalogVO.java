package com.ximo.springbootblogmaster.vo;


import com.ximo.springbootblogmaster.domain.Catalog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 分类VO对象.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogVO implements Serializable {

	private static final long serialVersionUID = -5326386345000506057L;

	/** 用户名 */
	private String username;
	/** 分类 */
	private Catalog catalog;

}
