package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.order.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDaoMem implements OrderDao {
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
    public void addOrder(Order order) {
        orderList.add(order);
    }

    @Override
    public Order getOrder(int id) {
        return orderList.stream().filter(order -> order.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void removeOrder(int id) {
        orderList.remove(getOrder(id));
    }
}
