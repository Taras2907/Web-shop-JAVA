package com.codecool.shop.dao;

import com.codecool.shop.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    List<User> getAll();
    User find(Integer id);
    User find(String email);
}
