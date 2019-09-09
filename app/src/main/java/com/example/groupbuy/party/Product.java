package com.example.groupbuy.party;

class Product {
    private String name;
    private String user;
    private double price;
    private boolean bought = false;
    private boolean mine = false;

    public Product(String name, String user, double price, boolean bought, boolean mine) {
        this.name = name;
        this.user = user;
        this.price = price;
        this.bought = bought;
        this.mine = mine;
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

    public boolean isBought() {
        return bought;
    }

    public boolean isMine() {
        return mine;
    }

    void changeStatus() {
        bought = !bought;
    }

    @Override
    public String toString() {
        return name;
    }
}
