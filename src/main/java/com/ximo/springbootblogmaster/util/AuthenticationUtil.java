package com.ximo.springbootblogmaster.util;

import org.springframework.security.core.Authentication;

import static com.ximo.springbootblogmaster.constant.CommonConstant.ANONYMOUS_USER;

/**
 * @author 朱文赵
 * @date 2018/5/9
 * @description
 */
public class AuthenticationUtil {

    /** 私有构造*/
    private AuthenticationUtil() {
        throw new UnsupportedOperationException();
    }

    /** 判断是否授权 不是匿名用户*/
    public static boolean isAuthentication(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated()
                && !ANONYMOUS_USER.equals(authentication.getPrincipal().toString());
    }


}
