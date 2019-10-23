package com.codecool.shop.controller;

import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.order.Order;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet (urlPatterns = {"/cart"})
public class Cart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("id"));

        OrderDaoMem orderDaoMem = OrderDaoMem.getInstance();
        Order order = new Order();
        ProductDaoMem productDaoMem =  ProductDaoMem.getInstance();
        Product productToAdd = productDaoMem.find(productId);
        Product productToAdd1 = productDaoMem.find(2);
        order.addProduct(productToAdd);
        order.addProduct(productToAdd1);

        orderDaoMem.addOrder(order);
        System.out.println(order.getHashMapArrayListForAjax());
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

        ProductDaoMem productDaoMem =  ProductDaoMem.getInstance();
        Product newProductToOurOrder =  productDaoMem.find(testOrder);
        OrderDaoMem orderDaoMem = OrderDaoMem.getInstance();

        orderDaoMem.getOrder(testOrder).addProduct(newProductToOurOrder);
        System.out.println(orderDaoMem.getOrder(testOrder).getProductList());
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader reader = request.getReader();
        int testOrder = Integer.parseInt(reader.readLine());

        ProductDaoMem productDaoMem =  ProductDaoMem.getInstance();
        Product productToRemove =  productDaoMem.find(testOrder);
        OrderDaoMem orderDaoMem = OrderDaoMem.getInstance();

        orderDaoMem.getOrder(testOrder).removeProduct(productToRemove);
        System.out.println(orderDaoMem.getOrder(testOrder).getProductList());
    }

}
