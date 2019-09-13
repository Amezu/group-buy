package com.example.groupbuy.party;

import com.example.groupbuy.connection.HttpRequest;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String name;
    private String user;
    private double price;
    private String category;
    private String description;
    private boolean bought = false;
    private int thumbsUpCount = 0;
    private boolean liked = false;

    public Product(String name, String user, double price) {
        this.name = name;
        this.user = user;
        this.price = price;
    }

    public Product(String id, String name, String user, double price, String category, String description, boolean bought, int thumbsUpCount, boolean liked) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.price = price;
        this.category = category;
        this.description = description;
        this.thumbsUpCount = thumbsUpCount;
        this.bought = bought;
        this.liked = liked;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public double getPrice() {
        return price;
    }

    public Object getCategory() { return category;}

    public Object getDescription() { return description;}

    public int getThumbsUpCount() {
        return thumbsUpCount;
    }

    public boolean isLiked() {
        return liked;
    }

    public boolean isBought() {
        return bought;
    }

    public boolean isMine(String userName) {
        return user.equals(userName);
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public void setThumbsUpCount(int thumbsUpCount) {
        this.thumbsUpCount = thumbsUpCount;
    }

    void changeStatus(boolean bought) {
        this.bought = bought;
    }

    @Override
    public String toString() {
        return name;
    }

}
