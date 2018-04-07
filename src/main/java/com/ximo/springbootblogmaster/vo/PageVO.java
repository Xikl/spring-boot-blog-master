package com.ximo.springbootblogmaster.vo;

import com.ximo.springbootblogmaster.enums.ResultEnums;
import lombok.Data;

/**
 * @author 朱文赵
 * @date 2018/4/6
 * @description 分页对象
 */
@Data
public class PageVO<T> {

    /** 返回码*/
    private Integer code;
    /** 返回数据*/
    private String msg;
    /** 当前页*/
    private Integer pageNum;
    /** 总页数*/
    private Long total;
    /** 具体返回的列表*/
    private T data;

    /** 私有构造*/
    private PageVO() {
    }

    /** 私有构造*/
    private PageVO(Integer code, String msg, Integer pageNum, Long total, T data) {
        this.code = code;
        this.msg = msg;
        this.pageNum = pageNum;
        this.total = total;
        this.data = data;
    }

    /** 私有构造*/
    private PageVO(ResultEnums enums, Integer pageNum, Long total, T data) {
        this.code = enums.getCode();
        this.msg = enums.getMsg();
        this.pageNum = pageNum;
        this.total = total;
        this.data = data;
    }

    /** 分页方法*/
    public static <T> PageVO<T> page(Integer pageNum, Long total, T data) {
        return new PageVO<T>(ResultEnums.SUCCESS, pageNum, total, data);
    }
}
