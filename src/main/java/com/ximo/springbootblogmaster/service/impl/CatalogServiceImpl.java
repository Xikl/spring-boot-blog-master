package com.ximo.springbootblogmaster.service.impl;

import com.ximo.springbootblogmaster.domain.Catalog;
import com.ximo.springbootblogmaster.domain.User;
import com.ximo.springbootblogmaster.enums.ResultEnums;
import com.ximo.springbootblogmaster.exception.BlogException;
import com.ximo.springbootblogmaster.repository.CatalogRepository;
import com.ximo.springbootblogmaster.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 分类service.
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Override
    public Catalog saveCatalog(Catalog catalog) {
        // 判断重复
        List<Catalog> list = catalogRepository.findByUserAndName(catalog.getUser(), catalog.getName());
        if (!CollectionUtils.isEmpty(list)) {
            throw new IllegalArgumentException("该分类已经存在了");
        }
        return catalogRepository.save(catalog);
    }

    @Override
    public void removeCatalog(Long id) {
        catalogRepository.deleteById(id);
    }

    @Override
    public Catalog getCatalogById(Long id) {
        return catalogRepository.findById(id).orElseThrow(() -> new BlogException(ResultEnums.RESOURCE_NOT_FOUND));
    }

    @Override
    public List<Catalog> listCatalogs(User user) {
        return catalogRepository.findByUser(user);
    }

}
