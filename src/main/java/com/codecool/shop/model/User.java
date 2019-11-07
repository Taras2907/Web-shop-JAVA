package com.codecool.shop.model;

public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private int orderId;

    public User(int id, String email, String password, Integer orderId, String name){
        this.id = id;
        this.email = email;
        this.password = password;
        this.orderId = orderId;
        this.name = name;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() { return email; }

    public String getName() { return name; }

    public String getPassword() { return password; }

    public int getOrderId() { return orderId; }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "email: %3$s" +
                        "password: %4$s",
                this.id,
                this.name,
                this.email,
                this.password
        );
    }
}
