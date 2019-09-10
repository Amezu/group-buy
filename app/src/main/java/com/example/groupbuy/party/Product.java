package com.example.groupbuy.party;

class Product {
    private String name;
    private String user;
    private double price;
    private boolean bought = false;
    private int thumbsUpCount = 0;
    private boolean liked = false;

    public Product(String name, String user, double price) {
        this.name = name;
        this.user = user;
        this.price = price;
    }

    public Product(String name, String user, double price, boolean bought, int thumbsUpCount, boolean liked) {
        this.name = name;
        this.user = user;
        this.price = price;
        this.thumbsUpCount = thumbsUpCount;
        this.bought = bought;
        this.liked = liked;
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

    public int getThumbsUpCount() {
        return thumbsUpCount;
    }

    public boolean isLiked() {
        return liked;
    }

    public boolean isBought() {
        return bought;
    }

    public boolean isMine() {
//        TODO: Replace mock
        return user.equals("Mark");
    }

    void changeStatus() {
        bought = !bought;
    }

    @Override
    public String toString() {
        return name;
    }
}
