package com.codecool.shop.model;

public class User {
    private int id;
    private String email;
    private String password;
    private String name;

    public User( String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() { return email; }

    public String getName() { return name; }

    public String getPassword() { return password; }


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
