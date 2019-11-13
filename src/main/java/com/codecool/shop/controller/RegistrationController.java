package com.codecool.shop.controller;

import com.codecool.shop.dao.Dao;
import com.codecool.shop.dao.database.UserDaoDB;
import com.codecool.shop.decryption.EncryptionDecryptionAES;
import com.codecool.shop.model.User;
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

@WebServlet(urlPatterns = {"/register"})
public class RegistrationController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> userProperties = Json.getUserProperties(req);
        String password = userProperties.get("password");
        String email = userProperties.get("login");
        String name = userProperties.get("name");

        EncryptionDecryptionAES aes = EncryptionDecryptionAES.getInstance();
        String hashedPassword = aes.encrypt(password);


        boolean isNotUser = isNotUser(email);
        if (isNotUser) {
            Dao<User> userDaoDB = UserDaoDB.getInstance();
            userDaoDB.add(new User(email, hashedPassword, name));
        }

        String isARegisteredUser = new Gson().toJson(isNotUser);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(isARegisteredUser);
        out.flush();
    }

    private boolean isNotUser(String email) {
        if (UserDaoDB.getInstance().findByEmail(email) != null) {

            String nameFromDatabase = UserDaoDB.getInstance().findByEmail(email).getEmail();

            if (email.equals(nameFromDatabase)) {
                return false;
            }
        }
        return true;
    }
}


