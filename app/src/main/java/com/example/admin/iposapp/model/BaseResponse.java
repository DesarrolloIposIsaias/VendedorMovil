package com.example.admin.iposapp.model;

/**
 * Created by sopor on 07/12/2017.
 */

public class BaseResponse<T> {
    private String message;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
