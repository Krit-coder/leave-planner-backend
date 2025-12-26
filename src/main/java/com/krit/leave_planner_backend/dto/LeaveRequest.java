package com.krit.leave_planner_backend.dto;

public class LeaveRequest {

    private Integer userId;
    private String date;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}