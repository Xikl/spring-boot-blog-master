package com.ximo.springbootblogmaster.repository.es;

import com.ximo.springbootblogmaster.domain.es.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description esBlog仓库.
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {
 
	/**
	 * 模糊查询(去重)
	 * @param title
	 * @param Summary
	 * @param content
	 * @param tags
	 * @param pageable
	 * @return
	 */
	Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title, String Summary, String content, String tags, Pageable pageable);

	/**
	 * 通过博客id查找es
	 *
	 * @param blogId 博客id
	 * @return esBlog
	 */
	EsBlog findByBlogId(Long blogId);

	List<EsBlog> findTop12ByUsername(String username);

	/**
	 * 通过tags的来查找
	 *
	 * @param tags 标签
	 * @return
	 */
	Page<EsBlog> findByTagsIn(Collection<String> tags, Pageable pageable);
}
