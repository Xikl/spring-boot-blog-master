package com.ximo.springbootblogmaster.exception;

import com.ximo.springbootblogmaster.enums.ResultEnums;
import lombok.Getter;

/**
 * @author 朱文赵
 * @date 2018/4/6
 * @description 博客自定义异常
 */
public class BlogException extends RuntimeException{

    @Getter
    private Integer code;

    public BlogException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BlogException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BlogException(ResultEnums enums) {
        super(enums.getMsg());
        this.code = enums.getCode();
    }

    public BlogException(ResultEnums enums, Throwable cause) {
        super(enums.getMsg(), cause);
        this.code = enums.getCode();
    }
}
