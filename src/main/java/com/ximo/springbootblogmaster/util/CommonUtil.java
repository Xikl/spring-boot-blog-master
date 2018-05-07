package com.ximo.springbootblogmaster.util;

import static com.ximo.springbootblogmaster.constant.CommonConstant.LIKE;

/**
 * @author 朱文赵
 * @date 2018/5/8
 * @description
 */
public class CommonUtil {

    public static String formatLikeString(String str) {
        return LIKE.concat(str).concat(LIKE);

    }


}
