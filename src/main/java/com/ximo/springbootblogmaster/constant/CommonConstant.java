package com.ximo.springbootblogmaster.constant;

/**
 * @author 朱文赵
 * @date 2018/4/7
 * @description 公共常量类
 */
public interface CommonConstant {

    /** like */
    String LIKE = "%";

    /** 热门的*/
    String HOT = "hot";

    /** 新的*/
    String NEW = "new";

    /** 推荐 */
    String RECOMMEND = "recommend";

    /** 分号*/
    String SEMICOLON = ";";

    String BLANK_CHARACTER = "";

    /** 角色授权id */
    Long ROLE_USER_AUTHORITY_ID = 2L;

    /** spring security中rememberMe中的私钥key */
    String KEY = "ximo.com";

    /** 匿名用户*/
    String ANONYMOUS_USER = "anonymousUser";

    /** 默认第一页 */
    Integer DEFAULT_PAGE_INDEX = 0;

    /** 默认第二页 */
    Integer DEFAULT_PAGE_SIZE = 5;

    /** 空关键字 */
    String EMPTY_KEYWORD = "";
}
