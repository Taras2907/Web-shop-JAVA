package com.codecool.shop.dao;

import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.User;

import java.util.List;

public interface Dao<T> {

    void add(T t);

    T find(int id);

    void remove(int id);

    List<T> getAll();
}
