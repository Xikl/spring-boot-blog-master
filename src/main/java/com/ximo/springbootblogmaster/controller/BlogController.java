package com.ximo.springbootblogmaster.controller;

import static com.ximo.springbootblogmaster.constant.CommonConstant.HOT;
import static com.ximo.springbootblogmaster.constant.CommonConstant.NEW;
import com.ximo.springbootblogmaster.domain.User;
import com.ximo.springbootblogmaster.domain.es.EsBlog;
import com.ximo.springbootblogmaster.service.EsBlogService;
import com.ximo.springbootblogmaster.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 博客控制器.
 */
@Controller
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private EsBlogService esBlogService;

    /**
     * 博客列表
     *
     * @param order
     * @param keyword
     * @param async
     * @param pageIndex
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping
    public String listEsBlogs(
            @RequestParam(value = "order", required = false, defaultValue = "new") String order,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "async", required = false) boolean async,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            Model model) {

        Page<EsBlog> page = null;
        List<EsBlog> list = null;
        // 系统初始化时，没有博客数据
        boolean isEmpty = true;
        try {
            // 最热查询
            if (HOT.equals(order)) {
                Sort sort = new Sort(Direction.DESC, "readSize", "commentSize", "voteSize", "createTime");
                Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
                page = esBlogService.listHottestEsBlogs(keyword, pageable);
            } else if (NEW.equals(order)) {
                // 最新查询
                Sort sort = new Sort(Direction.DESC, "createTime");
                Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
                page = esBlogService.listNewestEsBlogs(keyword, pageable);
            }

            isEmpty = false;
        } catch (Exception e) {
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = esBlogService.listEsBlogs(pageable);
        }
        // 当前所在页面数据列表
        list = page.getContent();


        model.addAttribute("order", order);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("blogList", list);

        // 首次访问页面才加载
        if (!async && !isEmpty) {
            List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
            model.addAttribute("newest", newest);
            List<EsBlog> hotest = esBlogService.listTop5HottestEsBlogs();
            model.addAttribute("hotest", hotest);
            List<TagVO> tags = esBlogService.listTop30Tags();
            model.addAttribute("tags", tags);
            List<User> users = esBlogService.listTop12Users();
            model.addAttribute("users", users);
        }

        return (async ? "/index :: #mainContainerRepleace" : "/index");
    }

    @GetMapping("/newest")
    public String listNewestEsBlogs(Model model) {
        List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
        model.addAttribute("newest", newest);
        return "newest";
    }

    @GetMapping("/hotest")
    public String listHottestEsBlogs(Model model) {
        List<EsBlog> hottest = esBlogService.listTop5HottestEsBlogs();
        model.addAttribute("hotest", hottest);
        return "hotest";
    }


}
