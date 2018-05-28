package com.ximo.springbootblogmaster.util;

import com.ximo.springbootblogmaster.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

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
     * 获得用户信息 可能为空
     *
     * @return 用户信息
     */
    public static Optional<User> getUserOrElse() {
        Authentication authentication = AuthenticationUtil.authentication();
        if (AuthenticationUtil.isAuthenticated(authentication)) {
            return Optional.of(AuthenticationUtil.getUser(authentication));
        }
        return Optional.empty();
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
    public static boolean isOwner(String username) {
        Authentication authentication = AuthenticationUtil.authentication();
        if (AuthenticationUtil.isAuthenticated(authentication)) {
            User principal = AuthenticationUtil.getUser(authentication);
            return isSamePerson(principal, username);
        }
        return false;
    }

    /** 判断是否是同一个人*/
    private static boolean isSamePerson(User principal, String username) {
        return principal != null && username.equals(principal.getUsername());
    }

}
