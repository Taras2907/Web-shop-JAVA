package com.codecool.shop.controller;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.localMemory.OrderDaoMem;
import com.codecool.shop.dao.implementation.localMemory.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    private ProductDao productDaoMem =  ProductDaoMem.getInstance();
    private OrderDao orderDaoMem = OrderDaoMem.getInstance();
    Order order = new Order();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String employeeJsonString = new Gson().toJson(order.getHashMapArrayListForAjax());
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(employeeJsonString);
        out.flush();
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        int testOrder = Integer.parseInt(reader.readLine());
        Product newProductToOurOrder =  productDaoMem.find(testOrder);
        orderDaoMem.getOrder(1).addProduct(newProductToOurOrder);
        System.out.println(orderDaoMem.getOrder(1).getProductList());


        //response for json
        String employeeJsonString = new Gson().toJson(" everything is ok");
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(employeeJsonString);
        out.flush();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        int testOrder = Integer.parseInt(reader.readLine());
        Product newProductToOurOrder =  productDaoMem.find(testOrder);

        orderDaoMem.getOrder(testOrder).addProduct(newProductToOurOrder);
        System.out.println(orderDaoMem.getOrder(testOrder).getProductList());

        //response for json
        String employeeJsonString = new Gson().toJson(" everything is ok");
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(employeeJsonString);
        out.flush();
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Integer>>(){}.getType();
        Map<String, Integer> myMap = gson.fromJson(reader.readLine(), type);

        int productId = myMap.get("id");
        int productQuantity = myMap.get("quantity");

        Product productToRemove =  productDaoMem.find(productId);

        orderDaoMem.getOrder(1).removeProduct(productToRemove, productQuantity);// get order number from database
        System.out.println(orderDaoMem.getOrder(1).getProductList());

//        response for json
        String employeeJsonString = gson.toJson("200 everything is ok");
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(employeeJsonString);
        out.flush();
    }

}
