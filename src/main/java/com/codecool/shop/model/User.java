package com.codecool.shop.model;

public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private String secretKey;

    public User(int id, String email, String password, String secretKey, String name){
        this.id = id;
        this.email = email;
        this.password = password;
        this.secretKey = secretKey;
        this.name = name;
    }

    public int getId() { return id; }

    public String getEmail() { return email; }

    public String getName() { return name; }

    public String getPassword() { return password; }

    public String getSecretKey() { return secretKey; }
}
