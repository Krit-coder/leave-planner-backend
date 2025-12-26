package com.krit.leave_planner_backend.dto;


public class LoginResponse {

    public LoginResponse(String token, Integer userId, String name, String role, Integer manager) {
        this.token = token;
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.managerAccess = manager;
    }

    private String token;
    private Integer userId;
    private String name;
    private String role;

    public Integer getManagerAccess() {
        return managerAccess;
    }

    public void setManagerAccess(Integer managerAccess) {
        this.managerAccess = managerAccess;
    }

    private Integer managerAccess;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
