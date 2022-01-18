package com.example.jwt.constant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResp<T>  {

    private int code;
    private String msg;
    private T data;
    private Long total;

    public RestResp() {
    }

    public RestResp(int code) {
        this.code = code;
    }

    public RestResp(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RestResp(int code, String msg, T data, Long total) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.total = total;
    }

    public RestResp(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static RestResp<?> ok() {
        return new RestResp<>(200, "success");
    }

    public static RestResp<?> ok(String msg) {
        return new RestResp<>(200, msg);
    }

    public static <T> RestResp<T> ok(T data) {
        return new RestResp<>(200, "success", data);
    }

    public static <T> RestResp<T> ok(T data, Long total) {
        return new RestResp<>(200, "success", data, total);
    }

    public static RestResp<?> error() {
        return new RestResp<>(500, "error");
    }

    public static RestResp<?> error(BaseErrorInfoInterface errorInfo) {
        return new RestResp<>( errorInfo.getResultCode(), errorInfo.getResultMsg());
    }
    public static RestResp<?> error(String msg) {
        return new RestResp<>(500, msg);
    }
    public static RestResp<?> error(int code,String msg) {
        return new RestResp<>(code, msg);
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
