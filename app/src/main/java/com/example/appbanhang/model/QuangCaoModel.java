package com.example.appbanhang.model;

import java.util.List;

public class QuangCaoModel {
    boolean success;
    String message;
    List<QuangCao> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<QuangCao> getResult() {
        return result;
    }

    public void setResult(List<QuangCao> result) {
        this.result = result;
    }
}
