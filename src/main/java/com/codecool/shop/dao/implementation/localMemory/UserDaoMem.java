package com.codecool.shop.dao.implementation.localMemory;

import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoMem implements UserDao {
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
    public User find(Integer id){
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    @Override
    public User find(String email){
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }

}
