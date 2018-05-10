package com.ximo.springbootblogmaster.controller;

import com.ximo.springbootblogmaster.domain.Blog;
import com.ximo.springbootblogmaster.domain.Catalog;
import com.ximo.springbootblogmaster.domain.User;
import com.ximo.springbootblogmaster.domain.Vote;
import com.ximo.springbootblogmaster.handler.ConstraintViolationExceptionHandler;
import com.ximo.springbootblogmaster.service.BlogService;
import com.ximo.springbootblogmaster.service.CatalogService;
import com.ximo.springbootblogmaster.service.UserService;
import com.ximo.springbootblogmaster.util.AuthenticationUtil;
import com.ximo.springbootblogmaster.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.ximo.springbootblogmaster.constant.CommonConstant.HOT;
import static com.ximo.springbootblogmaster.constant.CommonConstant.NEW;

/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 个人空间控制器.
 */
@Controller
@RequestMapping("/u")
public class UserspaceController {

    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 跳转页面
     *
     * @param username 用户名
     * @param model    model
     * @return 用户名
     */
    @GetMapping("/{username}")
    public String userSpace(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return "redirect:/u/" + username + "/blogs";
    }

    /**
     * 获得个人设置页面
     * 使用PreAuthorize注解 允许直接访问当前 Authentication对象 从SecurityContext中获得
     *
     * @param username 用户名
     * @param model    model
     * @return 用户页面
     */
    @GetMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView profile(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return new ModelAndView("/userspace/profile", "userModel", model);
    }

    /**
     * 保存个人设置
     *
     * @param username 用户名
     * @param user     用户
     * @return
     */
    @PostMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public String saveProfile(@PathVariable("username") String username, User user) {
        User originalUser = userService.getUserById(user.getId());
        originalUser.setEmail(user.getEmail());
        originalUser.setName(user.getName());

        // 判断密码是否做了变更
        String originalPassword = originalUser.getPassword();
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
        //将密码encode类改为注入的形式
        String newPassword = passwordEncoder.encode(user.getPassword());
        boolean isMatch = passwordEncoder.matches(originalPassword, newPassword);
        if (!isMatch) {
//            originalUser.setEncodePassword(user.getPassword());
            originalUser.setPassword(newPassword);
        }

        userService.saveUser(originalUser);
        return "redirect:/u/" + username + "/profile";
    }

    /**
     * 获取编辑头像的界面
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView avatar(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return new ModelAndView("/userspace/avatar", "userModel", model);
    }


    /**
     * 保存头像
     *
     * @param username
     * @param user
     * @return
     */
    @PostMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {
        String avatarUrl = user.getAvatar();

        User originalUser = userService.getUserById(user.getId());
        originalUser.setAvatar(avatarUrl);
        userService.saveUser(originalUser);

        return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
    }

    /**
     * 用户页面
     *
     * @param username
     * @param order
     * @param catalogId
     * @param keyword
     * @param async
     * @param pageIndex
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/{username}/blogs")
    public String listBlogsByOrder(@PathVariable("username") String username,
                                   @RequestParam(value = "order", required = false, defaultValue = "new") String order,
                                   @RequestParam(value = "catalog", required = false) Long catalogId,
                                   @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                   @RequestParam(value = "async", required = false) boolean async,
                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                   Model model) {

        User user = (User) userDetailsService.loadUserByUsername(username);

        Page<Blog> page = null;

        // 分类查询
        if (catalogId != null && catalogId > 0) {
            Catalog catalog = catalogService.getCatalogById(catalogId);
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = blogService.listBlogsByCatalog(catalog, pageable);
            order = "";
        } else if (HOT.equals(order)) {
            // 最热查询
            Sort sort = new Sort(Direction.DESC, "readSize", "commentSize", "voteSize");
            Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
            page = blogService.listBlogsByTitleVoteAndSort(user, keyword, pageable);
        } else if (NEW.equals(order)) {
            // 最新查询
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = blogService.listBlogsByTitleVote(user, keyword, pageable);
        }

        //当前所在页面数据列表
        List<Blog> list = page.getContent();

        model.addAttribute("user", user);
        model.addAttribute("order", order);
        model.addAttribute("catalogId", catalogId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("blogList", list);
        return (async ? "/userspace/u :: #mainContainerRepleace" : "/userspace/u");
    }

    /**
     * 获取博客展示界面
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{username}/blogs/{id}")
    public String getBlogById(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        User principal = null;
        Blog blog = blogService.getBlogById(id);

        // 每次读取，简单的可以认为阅读量增加1次
        blogService.readingIncrease(id);

        //todo 重构他
        // 判断操作用户是否是博客的所有者
        boolean isBlogOwner = false;
        Authentication authentication = AuthenticationUtil.authentication();
        if (AuthenticationUtil.isAuthenticated(authentication)) {
            principal = AuthenticationUtil.getUser(authentication);
            if (principal != null && username.equals(principal.getUsername())) {
                isBlogOwner = true;
            }
        }

        // 判断操作用户的点赞情况
        List<Vote> votes = blog.getVotes();
        //当前用户的点赞情况
        Vote currentVote = null;

        if (principal != null) {
            for (Vote vote : votes) {
                vote.getUser().getUsername().equals(principal.getUsername());
                currentVote = vote;
                break;
            }
        }

        model.addAttribute("isBlogOwner", isBlogOwner);
        model.addAttribute("blogModel", blog);
        model.addAttribute("currentVote", currentVote);

        return "/userspace/blog";
    }


    /**
     * 删除博客
     *
     * @param username 用户名
     * @param id       博客id
     * @return
     */
    @DeleteMapping("/{username}/blogs/{id}")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> deleteBlog(@PathVariable("username") String username, @PathVariable("id") Long id) {
        try {
            blogService.removeBlog(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        String redirectUrl = "/u/" + username + "/blogs";
        return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
    }

    /**
     * 获取新增博客的界面
     *
     * @param model
     * @return
     */
    @GetMapping("/{username}/blogs/edit")
    public ModelAndView createBlog(@PathVariable("username") String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);

        model.addAttribute("blog", new Blog(null, null, null));
        model.addAttribute("catalogs", catalogs);
        return new ModelAndView("/userspace/blogedit", "blogModel", model);
    }

    /**
     * 获取编辑博客的界面
     *
     * @param model
     * @return
     */
    @GetMapping("/{username}/blogs/edit/{id}")
    public ModelAndView editBlog(@PathVariable("username") String username, @PathVariable("id") Long id, Model model) {
        // 获取用户分类列表
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);

        model.addAttribute("blog", blogService.getBlogById(id));
        model.addAttribute("catalogs", catalogs);
        return new ModelAndView("/userspace/blogedit", "blogModel", model);
    }

    /**
     * 保存博客
     *
     * @param username
     * @param blog
     * @return
     */
    @PostMapping("/{username}/blogs/edit")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {
        // 对 Catalog 进行空处理
        if (blog.getCatalog().getId() == null) {
            return ResponseEntity.ok().body(new Response(false, "未选择分类"));
        }
        try {
            // 判断是修改还是新增
            if (blog.getId() != null) {
                Blog orignalBlog = blogService.getBlogById(blog.getId());
                orignalBlog.setTitle(blog.getTitle());
                orignalBlog.setContent(blog.getContent());
                orignalBlog.setSummary(blog.getSummary());
                orignalBlog.setCatalog(blog.getCatalog());
                orignalBlog.setTags(blog.getTags());
                blogService.saveBlog(orignalBlog);
            } else {
                User user = (User) userDetailsService.loadUserByUsername(username);
                blog.setUser(user);
                blogService.saveBlog(blog);
            }

        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.joinMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();
        return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
    }
}
