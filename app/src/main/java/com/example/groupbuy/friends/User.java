package com.example.groupbuy.friends;

import android.util.Log;


public class User{
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

    @Override
    public boolean equals(Object o) {
        Log.i("xD",this + " " + o);
        if (this.username.equals(o.toString())){
            return true;
        }
        else{
            return false;
        }
    }
}
