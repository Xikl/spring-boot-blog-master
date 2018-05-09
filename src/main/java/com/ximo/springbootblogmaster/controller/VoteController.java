package com.ximo.springbootblogmaster.controller;

import com.ximo.springbootblogmaster.domain.User;
import com.ximo.springbootblogmaster.handler.ConstraintViolationExceptionHandler;
import com.ximo.springbootblogmaster.service.BlogService;
import com.ximo.springbootblogmaster.service.VoteService;
import com.ximo.springbootblogmaster.util.AuthenticationUtil;
import com.ximo.springbootblogmaster.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.ConstraintViolationException;


/**
 * @author 朱文赵
 * @date 2018/4/8
 * @description 点赞控制器.
 */
@Controller
@RequestMapping("/votes")
public class VoteController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private VoteService voteService;

    /**
     * 发表点赞
     *
     * @param blogId
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")  // 指定角色权限才能操作方法
    public ResponseEntity<Response> createVote(Long blogId) {

        try {
            blogService.createVote(blogId);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.joinMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "点赞成功", null));
    }

    /**
     * 删除点赞
     *
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")  // 指定角色权限才能操作方法
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Long blogId) {

        boolean isOwner = false;
        User user = voteService.getVoteById(id).getUser();

        // 判断操作用户是否是点赞的所有者
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
            blogService.removeVote(blogId, id);
            voteService.removeVote(id);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.joinMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "取消点赞成功", null));
    }
}
