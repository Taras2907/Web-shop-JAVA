package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.Dao;
import com.codecool.shop.dao.database.ProductCategoryDaoDB;
import com.codecool.shop.dao.database.ProductDaoDB;
import com.codecool.shop.dao.database.SupplierDaoDB;
import com.codecool.shop.dao.database.UserDaoDB;
import com.codecool.shop.dao.implementation.localMemory.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.localMemory.ProductDaoMem;
import com.codecool.shop.dao.implementation.localMemory.SupplierDaoMem;
import com.codecool.shop.decryption.EncryptionDecryptionAES;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dao<Product> productDataStore = ProductDaoDB.getInstance();
        Dao<ProductCategory>  productCategoryDataStore = ProductCategoryDaoDB.getInstance();
        Dao<Supplier> supplierDaoMem = SupplierDaoDB.getInstance();


        List<Product> products = productDataStore.getAll();
        List<ProductCategory> categories = productCategoryDataStore.getAll();

        List<Supplier> suppliers = supplierDaoMem.getAll();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("products", products);
        context.setVariable("categories", categories);
        context.setVariable("suppliers", suppliers);
        engine.process("product/test.html", context, resp.getWriter());
        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
    }
}
