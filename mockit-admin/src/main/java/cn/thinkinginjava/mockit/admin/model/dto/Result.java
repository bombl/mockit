package cn.thinkinginjava.mockit.admin.model.dto;

import java.io.Serializable;


public class Result<T> implements Serializable {

    private static final long serialVersionUID = -5777465139846605810L;

    public static final int SUCCESS_CODE = 200;

    public static final int FAIL_CODE = 500;

    private int code;
    private String message;
    private T data;

    public Result() {
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(T data) {
        this.code = SUCCESS_CODE;
        this.data = data;
    }

    public static <T> Result<T> successful(T data) {
        return new Result<T>().setCode(SUCCESS_CODE).setData(data);
    }

    public static <T> Result<T> successful() {
        return new Result<T>().setCode(SUCCESS_CODE);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<T>().setCode(FAIL_CODE).setMessage(message);
    }

    public int getCode() {
        return code;
    }

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }
}
