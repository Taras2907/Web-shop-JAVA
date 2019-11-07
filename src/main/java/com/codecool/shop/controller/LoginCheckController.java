package com.codecool.shop.controller;

import com.codecool.shop.decryption.EncryptionDecryptionAES;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Map;

@WebServlet(urlPatterns = {"/login/check"})
public class LoginCheckController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isAUser = false;
        BufferedReader reader = req.getReader();
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> myMap = gson.fromJson(reader.readLine(), type);
        String password = myMap.get("password");
        String login = myMap.get("login");
        System.out.println(password + "   " + login);
        EncryptionDecryptionAES aes = EncryptionDecryptionAES.getInstance();
        //sql select password from user where userLogin = userName;


        String nameFromDatabase = "name";
        String passwordFromDatabase = "password";
//        if (login.equals(nameFromDatabase)){
//            isAUser = true;



        String isARegisteredUser = new Gson().toJson(isAUser);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(isARegisteredUser);
        out.flush();
    }
}
