package com.wuwei.homework.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class R<T> implements Serializable {

    //返回结果数据
    private T data;

    //返回结果提示信息
    private String msg;

    //返回结果代码
    private int code;

    private R(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> R<T> data(T data) {
        return data(data, "操作成功");
    }

    private R(int code, String message) {
        this(code, null, message);
    }


    //状态码=100，默认是返回成功
    public static <T> R<T> data(T data, String message) {
        return data(100, data, message);
    }

    public static <T> R<T> data(int code, T data, String message) {
        return new R(code, data, data == null ? "暂无承载数据" : message);
    }

    public static <T> R<T> fail(String message) {
        return new R(400, message);
    }

    public static <T> R<T> fail(int code, String message) {
        return new R(code, null, message);
    }


}
