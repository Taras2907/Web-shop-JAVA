package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.database.UserDaoDB;
import com.codecool.shop.decryption.EncryptionDecryptionAES;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

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

@WebServlet(urlPatterns = {"/login"})
public class LoginController  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("product/login.html", context, resp.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, String> myMap = gson.fromJson(reader.readLine(), type);
        String password = myMap.get("password");
        String email = myMap.get("login");


        EncryptionDecryptionAES aes = EncryptionDecryptionAES.getInstance();
        String hashedPassword = aes.encrypt(password);

        boolean isUser = isUser(email, hashedPassword, req);
        String isARegisteredUser = new Gson().toJson(isUser);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(isARegisteredUser);
        out.flush();
    }

    private boolean isUser(String email, String password, HttpServletRequest req){
        if (UserDaoDB.getInstance().findByEmail(email) != null) {

            String emailFromDatabase = UserDaoDB.getInstance().findByEmail(email).getEmail();
            String passwordFromDatabase = UserDaoDB.getInstance().findByEmail(email).getPassword();
            String nameFromDatabase = UserDaoDB.getInstance().findByEmail(email).getName();

            if (email.equals(emailFromDatabase) && password.equals(passwordFromDatabase)) {
                req.getSession().putValue("user", nameFromDatabase);
                return true;
            }
        }
        return false;
    }


}
