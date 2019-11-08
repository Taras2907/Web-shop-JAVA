package com.codecool.shop.dao.implementation.localMemory;

import com.codecool.shop.dao.Dao;
import com.codecool.shop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDaoMem implements Dao<Order> {
    private List<Order> orderList = new ArrayList<>();
    private static OrderDaoMem instance = null;

    private OrderDaoMem(){

    }
    public static OrderDaoMem getInstance(){
        if(instance == null){
            instance = new OrderDaoMem();
        }
        return instance;
    }
    @Override
    public void add(Order order) {
        orderList.add(order);
    }

    @Override
    public Order find(int id) {
        return orderList.stream().filter(order -> order.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        orderList.remove(find(id));
    }

    @Override
    public List<Order> getAll() {
        return orderList;
    }
}
