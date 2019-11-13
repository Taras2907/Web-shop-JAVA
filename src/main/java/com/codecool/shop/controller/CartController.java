package com.codecool.shop.controller;

import com.codecool.shop.dao.Dao;
import com.codecool.shop.dao.implementation.localMemory.OrderDaoMem;
import com.codecool.shop.dao.implementation.localMemory.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Map;

@WebServlet (urlPatterns = {"/cart"})
public class CartController extends HttpServlet {
//    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private Dao<Product> productDaoMem =  ProductDaoMem.getInstance();
    private Dao<Order> orderDaoMem = OrderDaoMem.getInstance();
    private Order order = new Order();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String jsonResp = new Gson().toJson(order.getHashMapArrayListForAjax());
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonResp);
        out.flush();
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        int productId = Integer.parseInt(reader.readLine());

//        logger.info("Id of product that should be added to order {}", productId);

        Product newProductToOurOrder =  productDaoMem.find(productId);
//        logger.info("Product received from database is  {}", newProductToOurOrder);
        orderDaoMem.find(1).addProduct(newProductToOurOrder);



        //response for json
        String jsonResp = new Gson().toJson(" everything is ok");
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonResp);
        out.flush();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        BufferedReader reader = request.getReader();
//        int productId = Integer.parseInt(reader.readLine());
//
//        logger.info("Id of product that should be added to order {}", productId);
//        Product newProductToOurOrder = productDaoMem.find(productId);
//        logger.info("Id of product that should be removed from order {}", productId);
//        orderDaoMem.find(1).addProduct(newProductToOurOrder);
//
//        String jsonResp = new Gson().toJson(" everything is ok");
//        PrintWriter out = response.getWriter();
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        out.print(jsonResp);
//        out.flush();
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Integer>>(){}.getType();
        Map<String, Integer> myMap = gson.fromJson(reader.readLine(), type);

        int productId = myMap.get("id");
        int productQuantity = myMap.get("quantity");

//        logger.info("Id of product that should be removed to order is {} and quantity to remove is {}", productId, productQuantity);
        Product productToRemove = productDaoMem.find(productId);
//        logger.info("ID of product that should be removed from order is {}", productId);
        orderDaoMem.find(1).removeProduct(productToRemove, productQuantity);// get order number from database

//        response for json
        String jsonResp = gson.toJson("everything is ok");
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonResp);
        out.flush();
    }

}
