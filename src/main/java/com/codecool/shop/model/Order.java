package com.codecool.shop.model;

import com.codecool.shop.dao.implementation.localMemory.OrderDaoMem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Order {
    private Map <Product, Integer>  productList = new HashMap<>();
    private int id;
    private static int _id = 1;

    private static final Logger logger = LoggerFactory.getLogger(Order.class);

    public Order() {
        this.id = _id;
        OrderDaoMem.getInstance().add(this);
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
            logger.info("Removed {} from order.", product.getName());
        }
    }

    public void addProduct(Product product) {
        if (productList.containsKey(product)){
            productList.put(product, productList.get(product) + 1);
            logger.info("Added {} to cart", product.getName());
        }else{
            productList.put(product, 1);
            logger.info("Added {} to cart", product.getName());
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
