package com.codecool.shop.controller;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.decryption.EncryptionDecryptionAES;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/register"})
public class RegistrationController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        engine.process("product/login.html", context, resp.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("name");
        String email = req.getParameter("email");
        String userName = req.getParameter("password");

        EncryptionDecryptionAES aes = EncryptionDecryptionAES.getInstance();
        Map<String, Object> keyEncText = aes.encrypt(password);

        String encryptedPassword = (String) keyEncText.get("encryptedText");
        SecretKey secretKey = (SecretKey)  keyEncText.get("secretKey");


        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        engine.process("product/login.html", context, resp.getWriter());
    }
}


