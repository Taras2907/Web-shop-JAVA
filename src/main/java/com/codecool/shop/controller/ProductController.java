package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.util.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/", "/product"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

        List<Product> products;

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        String categoryId = req.getParameter("category");

        if (categoryId != null) {
            try {
                ProductCategory productCategory = productCategoryDataStore.find(Integer.parseInt(categoryId));
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath());
                return;
            }
            ProductCategory productCategory = productCategoryDataStore.find(Integer.parseInt(categoryId));
            products = productDataStore.getBy(productCategory);
            if (products.size() > 0) {
                context.setVariable("category", productCategory);
                context.setVariable("products", products);
                engine.process("product/filtered.html", context, resp.getWriter());
            } else {
                resp.sendRedirect(req.getContextPath());
            }
        } else {
            products = productDataStore.getAll();
            context.setVariable("products", products);
            engine.process("product/index.html", context, resp.getWriter());
        }


        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
    }
}
