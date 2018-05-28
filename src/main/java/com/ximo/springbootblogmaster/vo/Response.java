package com.ximo.springbootblogmaster.vo;


import com.ximo.springbootblogmaster.enums.ResultEnums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 朱文赵
 * @date 2018/4/6
 * @description 响应 值对象.
 */
public class Response {


    private boolean success;
    private String message;
    private Object body;

    /**
     * 响应处理是否成功
     */
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 响应处理的消息
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 响应处理的返回内容
     */
    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Response(boolean success, String message, Object body) {
        this.success = success;
        this.message = message;
        this.body = body;
    }

    public static Response responseOne(String key, Object value) {
        Map<String, Object> resultMap = new HashMap<>(1);
        resultMap.put(key, value);
        return new Response(true, ResultEnums.SUCCESS.getMsg(), resultMap);
    }

}
