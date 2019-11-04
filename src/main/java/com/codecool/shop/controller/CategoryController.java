package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/product"})
public class CategoryController extends HttpServlet {
    private ProductDao productDataStore = ProductDaoMem.getInstance();
    private ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        if (isValidRequestParameters(req)) {
            setContext(context, req);
            engine.process("product/index.html", context, resp.getWriter());
        } else {
            resp.sendRedirect(req.getContextPath());
        }
    }

    //TODO:
    private boolean isValidRequestParameters(HttpServletRequest req, String parameter) {
        String supplierId = req.getParameter(parameter);
        if (supplierId != null && canConvertToInteger(supplierId)){
            Product supplierProducts = productDataStore.getBy()
        }

        String categoryId = req.getParameter(parameter);
        if (categoryId != null && canConvertToInteger(categoryId)) {
            ProductCategory productCategory = productCategoryDataStore.find(Integer.parseInt(categoryId));
            List<Product> products = productDataStore.getBy(productCategory);
            return products.size() > 0;
        }
        return false;
    }

    private boolean canConvertToInteger(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void setContext(WebContext context, HttpServletRequest req) {
        String categoryId = req.getParameter("category");
        ProductCategory productCategory = productCategoryDataStore.find(Integer.parseInt(categoryId));
        List<Product> products = productDataStore.getBy(productCategory);
        List<ProductCategory> categories = productCategoryDataStore.getAll();
        context.setVariable("category", productCategory);
        context.setVariable("products", products);
        context.setVariable("categories", categories);
    }
}