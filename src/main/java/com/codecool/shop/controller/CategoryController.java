package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.Dao;
import com.codecool.shop.dao.database.ProductCategoryDaoDB;
import com.codecool.shop.dao.database.ProductDaoDB;
import com.codecool.shop.dao.database.SupplierDaoDB;
import com.codecool.shop.dao.database.UserDaoDB;
import com.codecool.shop.dao.implementation.localMemory.ProductDaoMem;
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

@WebServlet(urlPatterns = {"/product"})
public class CategoryController extends HttpServlet {
    private Dao<ProductCategory> productCategoryDataStore = ProductCategoryDaoDB.getInstance();
    private Dao<Supplier> supplierDaoMem = SupplierDaoDB.getInstance();


    //setting up users

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        if (isValidRequestParameters(req)) {
            setContext(context, req);
            engine.process("product/test.html", context, resp.getWriter());
        } else {
            resp.sendRedirect(req.getContextPath());
        }
    }

    private boolean isValidRequestParameters(HttpServletRequest req) {
        String supplierId = req.getParameter("supplier");
        if (supplierId != null && canConvertToInteger(supplierId)) {
            Supplier supplier = supplierDaoMem.find(Integer.parseInt(supplierId));
            List<Product> productsBySupplier = ProductDaoMem.getInstance().getBy(supplier);
            return productsBySupplier.size() > 0;
        }

        String categoryId = req.getParameter("category");
        if (categoryId != null && canConvertToInteger(categoryId)) {
            ProductCategory productCategory = productCategoryDataStore.find(Integer.parseInt(categoryId));
            List<Product> products = ProductDaoMem.getInstance().getBy(productCategory);
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

        List<Product> products;
        List<ProductCategory> categories = productCategoryDataStore.getAll();
        List<Supplier> suppliers = supplierDaoMem.getAll();

        //TODO: move the following validation into method

        String supplierId = req.getParameter("supplier");
        Supplier supplier;
        if (supplierId != null) {
            supplier = supplierDaoMem.find(Integer.parseInt(supplierId));
            products = supplier.getProducts();
            context.setVariable("products", products);
        }

        String categoryId = req.getParameter("category");
        ProductCategory productCategory;
        if (categoryId != null) {
            productCategory = productCategoryDataStore.find(Integer.parseInt(categoryId));
            context.setVariable("category", productCategory);
            products = ProductDaoMem.getInstance().getBy(productCategory);
            context.setVariable("products", products);
        }

        context.setVariable("suppliers", suppliers);
        context.setVariable("categories", categories);
    }
}