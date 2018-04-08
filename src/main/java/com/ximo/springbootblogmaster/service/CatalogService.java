package com.ximo.springbootblogmaster.service;

import com.ximo.springbootblogmaster.domain.Catalog;
import com.ximo.springbootblogmaster.domain.User;

import java.util.List;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 分类服务接口.
 */
public interface CatalogService {
	/**
	 * 保存Catalog
	 * @param catalog
	 * @return
	 */
	Catalog saveCatalog(Catalog catalog);
	
	/**
	 * 删除Catalog
	 * @param id
	 * @return
	 */
	void removeCatalog(Long id);

	/**
	 * 根据id获取Catalog
	 * @param id
	 * @return
	 */
	Catalog getCatalogById(Long id);
	
	/**
	 * 获取Catalog列表
	 * @return
	 */
	List<Catalog> listCatalogs(User user);
}
