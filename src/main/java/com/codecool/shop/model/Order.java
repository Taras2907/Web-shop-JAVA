package com.codecool.shop.model;

import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private Map <Product, Integer>  productList = new HashMap<>();
    private int id;
    private static int _id = 1;

    public Order() {
        this.id = _id;
        OrderDaoMem.getInstance().addOrder(this);
        _id++;
    }

    public Map<Product, Integer>  getProductList() {
        return productList;
    }

    public void removeProduct(Product product, int productQuantity) {
        if (productList.get(product) > 1 && productQuantity == 1){
            productList.put(product, productList.get(product) - 1);
        }else {
            productList.remove(product);
        }
    }

    public void addProduct(Product product) {
        if (productList.containsKey(product)){
            productList.put(product, productList.get(product) + 1);
        }else{
            productList.put(product, 1);
        }
    }




    public int getId() {
        return id;
    }

    public ArrayList<HashMap<String, String>> getHashMapArrayListForAjax() {

        ArrayList<HashMap<String, String>> mapForAjax = new ArrayList<>();

        for(Map.Entry<Product, Integer> entry : productList.entrySet()){

            HashMap<String , String> map = new HashMap<>();
            Product product = entry.getKey();

            map.put("name", product.getName());
            map.put("id", String.valueOf(product.getId()));
            map.put("quantity", entry.getValue().toString());
            map.put("price", product.getPrice());

            mapForAjax.add(map);
        }
        return mapForAjax;
    }
}
