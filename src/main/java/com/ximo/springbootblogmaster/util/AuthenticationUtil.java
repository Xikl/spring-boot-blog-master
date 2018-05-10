package com.ximo.springbootblogmaster.util;

import com.ximo.springbootblogmaster.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.ximo.springbootblogmaster.constant.CommonConstant.ANONYMOUS_USER;

/**
 * @author 朱文赵
 * @date 2018/5/9
 * @description
 */
public class AuthenticationUtil {

    /**
     * 私有构造
     */
    private AuthenticationUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * 判断是否授权 不是匿名用户
     */
    public static boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated()
                && !ANONYMOUS_USER.equals(authentication.getPrincipal().toString());
    }

    /**
     * 从各种SecurityContextHolderStrategy 默认一般是threadLocal机制 获得用户
     */
    public static User getUser() {
        return (User) authentication().getPrincipal();
    }


    /**
     * 从各种SecurityContextHolderStrategy 默认一般是threadLocal机制 获得用户
     */
    public static User getUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }

    /** 获得认证信息 从上下文中*/
    public static Authentication authentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /** 判断是否是所有者*/
    public static boolean isOwner(User user) {
        Authentication authentication = AuthenticationUtil.authentication();
        if (AuthenticationUtil.isAuthenticated(authentication)) {
            User principal = AuthenticationUtil.getUser(authentication);
            return isOwner(principal, user);
        }
        return false;
    }

    /** 判断是否是所有者*/
    public static boolean isOwner(String username) {
        Authentication authentication = AuthenticationUtil.authentication();
        if (AuthenticationUtil.isAuthenticated(authentication)) {
            User principal = AuthenticationUtil.getUser(authentication);
            return isOwner(principal, username);
        }
        return false;
    }

    /** 判断是否是同一个人*/
    private static boolean isOwner(User principal, User user) {
        return principal != null && user.getUsername().equals(principal.getUsername());
    }

    /** 判断是否是同一个人*/
    private static boolean isOwner(User principal, String username) {
        return principal != null && username.equals(principal.getUsername());
    }

}
