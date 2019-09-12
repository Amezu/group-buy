package com.example.groupbuy.friends;

public class User {
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }


    private String userId;
    private String username;
}
