package com.cloud.config.domain;

/**
 * Project: ConfigServer
 * Module Desc:com.juntu.config.vo
 * User: zjprevenge
 * Date: 2016/11/21
 * Time: 16:30
 */
public class JsonData<T> {

    private int code;
    private String msg;
    private T data;

    public JsonData() {
    }

    public JsonData(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static JsonData success() {
        return new JsonData(200, "success", null);
    }

    public static JsonData failure() {
        return new JsonData(500, "failure", null);
    }

    public int getCode() {
        return code;
    }

    public JsonData setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public JsonData setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public JsonData setData(T data) {
        this.data = data;
        return this;
    }
}
