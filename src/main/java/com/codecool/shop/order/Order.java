package com.codecool.shop.order;

import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Product> productList = new ArrayList<>();
    private int id;
    private static int _id = 1;
    public Order(){
        this.id = _id;
        _id ++;
    }

    public List<Product> getProductList() {
        return productList;
    }
    public void removeProduct(Product product){
        productList.remove(product);
    }
    public void addProduct(Product product){
        productList.add(product);
    }

}
