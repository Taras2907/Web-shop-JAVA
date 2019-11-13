package com.codecool.shop.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class Json {
    public static Map<String, String> getUserProperties(HttpServletRequest req){
        Map<String, String> userProperties = null;
        try {
            BufferedReader reader = req.getReader();
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            userProperties = gson.fromJson(reader.readLine(), type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userProperties;
    }
}
