package com.codecool.shop.dao.implementation.localMemory;

import com.codecool.shop.dao.Dao;
import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoMem implements Dao<User> {
    private List<User> users = new ArrayList<>();
    private static UserDaoMem instance = null;

    public static UserDaoMem getInstance() {
        if (instance == null) {
            instance = new UserDaoMem();
        }
        return instance;
    }

    @Override
    public void add(User user){
        users.add(user);
    }

    @Override
    public List<User> getAll(){
        return users;
    }

    @Override
    public User find(int id){
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    public User findByEmail(String email){
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {

    }

}
