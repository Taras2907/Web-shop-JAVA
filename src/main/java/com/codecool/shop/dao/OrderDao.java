package com.codecool.shop.dao;

import com.codecool.shop.order.Order;

public interface OrderDao {
    void addOrder(Order order);
    Order getOrder(int id);
    void removeOrder(int id);
}
