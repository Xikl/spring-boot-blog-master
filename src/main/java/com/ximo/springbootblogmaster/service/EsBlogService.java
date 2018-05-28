package com.ximo.springbootblogmaster.service;


import com.ximo.springbootblogmaster.domain.User;
import com.ximo.springbootblogmaster.domain.es.EsBlog;
import com.ximo.springbootblogmaster.vo.TagVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description esBlog服务接口.
 */
public interface EsBlogService {

    /**
     * 删除Blog
     *
     * @param id
     * @return
     */
    void removeEsBlog(String id);

    /**
     * 更新 EsBlog
     *
     * @param esBlog
     * @return
     */
    EsBlog updateEsBlog(EsBlog esBlog);

    /**
     * 根据id获取Blog
     *
     * @param blogId
     * @return
     */
    EsBlog getEsBlogByBlogId(Long blogId);

    /**
     * 最新博客列表，分页
     *
     * @param keyword 关键字
     * @param pageIndex 当前页
     * @param pageSize 一页多少条
     * @return 最新的博客列表
     */
    Page<EsBlog> listNewestEsBlogs(String keyword, Integer pageIndex, Integer pageSize);

    /**
     * 最热博客列表，分页
     *
     * @param keyword
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<EsBlog> listHottestEsBlogs(String keyword, Integer pageIndex, Integer pageSize);

    Page<EsBlog> listRecommendEsBlogs(Integer pageIndex, Integer pageSize);

    /**
     * 博客列表，分页
     *
     * @param pageable
     * @return
     */
    Page<EsBlog> listEsBlogs(Pageable pageable);

    /**
     * 最新前5
     *
     * @return
     */
    List<EsBlog> listTop5NewestEsBlogs();

    /**
     * 最热前5
     *
     * @return
     */
    List<EsBlog> listTop5HottestEsBlogs();

    /**
     * 最热前 30 标签
     *
     * @return
     */
    List<TagVO> listTop30Tags();

    /**
     * 最热前12用户
     *
     * @return
     */
    List<User> listTop12Users();
}
