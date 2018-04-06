package com.ximo.springbootblogmaster.vo;

import com.ximo.springbootblogmaster.enums.ResultEnums;
import lombok.Data;

/**
 * @author 朱文赵
 * @date 2018/4/6
 * @description 同意返回对象
 */
@Data
public class ResultVO<T> {

    private static final Integer SUCCESS_CODE = 0;

    /** 返回码*/
    private Integer code;
    /** 返回信息*/
    private String msg;
    /** 返回数据*/
    private T data;

    /** 私有构造*/
    private ResultVO() {
    }

    /** 私有构造*/
    private ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /** 私有构造*/
    private ResultVO(ResultEnums enums, T data) {
        this.code = enums.getCode();
        this.msg = enums.getMsg();
        this.data = data;
    }

    /** 成功方法 默认返回信息-成功 指定返回数据*/
    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<>(ResultEnums.SUCCESS, data);
    }

    /** 成功方法 无返回数据*/
    public static <T> ResultVO<T> success() {
        return new ResultVO<>(ResultEnums.SUCCESS, null);
    }

    /** 成功方法 指定返回信息 和 返回数据*/
    public static <T> ResultVO<T> success(String msg, T data) {
        return new ResultVO<>(SUCCESS_CODE, msg, data);
    }

    /** 错误方法*/
    public static <T> ResultVO<T> error(ResultEnums enums) {
        return new ResultVO<>(enums.getCode(), enums.getMsg(), null);
    }

    /** 错误方法*/
    public static <T> ResultVO<T> error(Integer code, String msg) {
        return new ResultVO<>(code, msg, null);
    }


}
