package com.codecool.shop.order;

import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private ArrayList<Map<String, String >> hashMapArrayListForAjax = new ArrayList<>();
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
        Map<String, String> productForCart = new HashMap<>();
        String id = String.valueOf(product.getId());

        productForCart.put("id", id);
        productForCart.put("price", product.getPrice());
        productForCart.put("name", product.getName());

        hashMapArrayListForAjax.add(productForCart);
        productList.add(product);
    }

    public int getId() {
        return id;
    }
    public ArrayList<Map<String, String >> getHashMapArrayListForAjax(){
        return hashMapArrayListForAjax;
    }
}
