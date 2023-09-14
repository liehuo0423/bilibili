package com.bilibili.domain;

import java.nio.channels.Pipe;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public class Response<T> {
    private Integer code;
    private String msg;
    private T data;

    public Response(T data) {
        this.data = data;
        this.code = 200;
        this.msg = "操作成功";
    }

    public Response(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Response<String> success(String data) {
        return new Response<>(data);
    }

    public static Response<String> success() {
        return new Response<>(null);
    }

    public static Response<String> fail() {
        return new Response<>(400, "failure");
    }

    public static Response<String> fail(Integer code, String msg) {
        return new Response<>(code, msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
