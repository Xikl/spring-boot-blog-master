package com.ximo.springbootblogmaster.controller;

import com.ximo.springbootblogmaster.domain.Blog;
import com.ximo.springbootblogmaster.domain.Comment;
import com.ximo.springbootblogmaster.domain.User;
import com.ximo.springbootblogmaster.handler.ConstraintViolationExceptionHandler;
import com.ximo.springbootblogmaster.service.BlogService;
import com.ximo.springbootblogmaster.service.CommentService;
import com.ximo.springbootblogmaster.util.AuthenticationUtil;
import com.ximo.springbootblogmaster.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;


/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 评论控制器.
 */
@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    /**
     * 获取评论列表
     *
     * @param blogId
     * @param model
     * @return
     */
    @GetMapping
    public String listComments(@RequestParam(value = "blogId") Long blogId, Model model) {
        Blog blog = blogService.getBlogById(blogId);
        List<Comment> comments = blog.getComments();

        // 判断操作用户是否是评论的所有者
        String commentOwner = "";
        Authentication authentication = AuthenticationUtil.authentication();
        if (AuthenticationUtil.isAuthenticated(authentication)) {
            User principal = AuthenticationUtil.getUser(authentication);
            if (principal != null) {
                commentOwner = principal.getUsername();
            }
        }

        model.addAttribute("commentOwner", commentOwner);
        model.addAttribute("comments", comments);
        return "/userspace/blog :: #mainContainerRepleace";
    }

    /**
     * 发表评论
     *
     * @param blogId
     * @param commentContent
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Response> createComment(Long blogId, String commentContent) {

        try {
            blogService.createComment(blogId, commentContent);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.joinMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", null));
    }

    /**
     * 删除评论
     *
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Long blogId) {

        boolean isOwner = false;
        User user = commentService.getCommentById(id).getUser();

        // 判断操作用户是否是评论的所有者
        Authentication authentication = AuthenticationUtil.authentication();
        if (AuthenticationUtil.isAuthenticated(authentication)) {
            User principal = AuthenticationUtil.getUser(authentication);
            if (principal != null && user.getUsername().equals(principal.getUsername())) {
                isOwner = true;
            }
        }

        if (!isOwner) {
            return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
        }

        try {
            blogService.removeComment(blogId, id);
            commentService.removeComment(id);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.joinMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", null));
    }
}
