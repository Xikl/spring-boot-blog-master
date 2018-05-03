package com.ximo.springbootblogmaster.enums;

import lombok.Getter;

/**
 * @author 朱文赵
 * @date 2018/4/6
 * @description
 */
@Getter
public enum ResultEnums {

    /** 成功*/
    SUCCESS(0, "成功"),
    INNER_ERROR(-1, "内部错误"),
    AUTHORITY_NOT_FOUND(1, "没有该权限")
    ;


    private Integer code;

    private String msg;

    ResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
