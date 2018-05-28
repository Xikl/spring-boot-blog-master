package com.ximo.springbootblogmaster.service.impl;


import com.ximo.springbootblogmaster.domain.Catalog;
import com.ximo.springbootblogmaster.domain.User;
import com.ximo.springbootblogmaster.domain.es.EsBlog;
import com.ximo.springbootblogmaster.repository.es.EsBlogRepository;
import com.ximo.springbootblogmaster.service.CatalogService;
import com.ximo.springbootblogmaster.service.EsBlogService;
import com.ximo.springbootblogmaster.service.UserService;
import com.ximo.springbootblogmaster.service.VoteService;
import com.ximo.springbootblogmaster.util.AuthenticationUtil;
import com.ximo.springbootblogmaster.vo.TagVO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.search.SearchParseException;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ximo.springbootblogmaster.constant.CommonConstant.*;
import static java.util.stream.Collectors.toList;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description esBlog服务.
 */
@Service
@Slf4j
public class EsBlogServiceImpl implements EsBlogService {

    @Autowired
    private EsBlogRepository esBlogRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private VoteService voteService;



    /**
     * 删博客
     *
     * @param id 博客id
     */
    @Override
    public void removeEsBlog(String id) {
        esBlogRepository.deleteById(id);
    }

    /**
     * 更新和保存blog
     *
     * @param esBlog 博客
     * @return 博客
     */
    @Override
    public EsBlog updateEsBlog(EsBlog esBlog) {
        return esBlogRepository.save(esBlog);
    }

    /**
     * @param blogId
     * @return
     */
    @Override
    public EsBlog getEsBlogByBlogId(Long blogId) {
        return esBlogRepository.findByBlogId(blogId);
    }

    @Override
    public Page<EsBlog> listNewestEsBlogs(String keyword, Integer pageIndex, Integer pageSize) throws SearchParseException {
        Sort sort = new Sort(Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
        return esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageable);
    }

    /**
     * @param keyword
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws SearchParseException
     */
    @Override
    public Page<EsBlog> listHottestEsBlogs(String keyword, Integer pageIndex, Integer pageSize) throws SearchParseException {
        Sort sort = new Sort(Direction.DESC, "readSize", "commentSize", "voteSize", "createTime");
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
        return esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword, keyword, keyword, keyword, pageable);
    }

    @Override
    public Page<EsBlog> listRecommendEsBlogs(Integer pageIndex, Integer pageSize) {
        AuthenticationUtil.getUserOrElse().ifPresent(user -> {
            //拿到这个用户的分类
            List<Catalog> catalogs = catalogService.listCatalogs(user);

        });
        //未登录我们直接显示最热的文章
        return listHottestEsBlogs(BLANK_CHARACTER, pageIndex, pageSize);
    }

    @Override
    public Page<EsBlog> listEsBlogs(Pageable pageable) {
        return esBlogRepository.findAll(pageable);
    }


    /**
     * 最新前5
     *
     * @param
     * @return
     */
    @Override
    public List<EsBlog> listTop5NewestEsBlogs() {
        return listNewestEsBlogs(EMPTY_KEYWORD, DEFAULT_PAGE_INDEX, DEFAULT_PAGE_SIZE).getContent();
    }

    /**
     * 最热前5
     *
     * @param
     * @return
     */
    @Override
    public List<EsBlog> listTop5HottestEsBlogs() {
        return listHottestEsBlogs(EMPTY_KEYWORD, DEFAULT_PAGE_INDEX, DEFAULT_PAGE_SIZE).getContent();
    }

    @Override
    public List<TagVO> listTop30Tags() {
        // given
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withSearchType(SearchType.QUERY_THEN_FETCH)
                .withIndices("blog").withTypes("blog")
                .addAggregation(terms("tags").field("tags").order(Terms.Order.count(false)).size(30))
                .build();
        // when
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, SearchResponse::getAggregations);

        StringTerms modelTerms = (StringTerms) aggregations.asMap().get("tags");

        return modelTerms.getBuckets().stream()
                .map(actionTypeBucket -> new TagVO(actionTypeBucket.getKey().toString(), actionTypeBucket.getDocCount()))
                .collect(toList());
    }

    @Override
    public List<User> listTop12Users() {
        // given
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withSearchType(SearchType.QUERY_THEN_FETCH)
                .withIndices("blog").withTypes("blog")
                //聚和查询 terms 指定查询的名字 可以随便起 相当于 groupBy 分组 查询
                .addAggregation(terms("users").field("username").order(Terms.Order.count(false)).size(12))
                .build();
        // when
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, SearchResponse::getAggregations);

        StringTerms modelTerms = (StringTerms) aggregations.asMap().get("users");
        List<String> usernameList = modelTerms.getBuckets().stream()
                .map(actionTypeBucket -> actionTypeBucket.getKey().toString())
                .collect(toList());
        return userService.listUsersByUsername(usernameList);
    }
}
