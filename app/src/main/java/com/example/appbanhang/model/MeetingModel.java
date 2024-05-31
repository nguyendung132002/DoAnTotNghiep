package com.example.appbanhang.model;

import java.util.List;


public class MeetingModel {
    boolean success;
    String message;
    List<Meeting> result;

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

    public List<Meeting> getResult() {
        return result;
    }

    public void setResult(List<Meeting> result) {
        this.result = result;
    }
}
