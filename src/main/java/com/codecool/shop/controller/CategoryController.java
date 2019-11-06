package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.localMemory.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.localMemory.ProductDaoMem;
import com.codecool.shop.dao.implementation.localMemory.SupplierDaoMem;
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
    private SupplierDao supplierDaoMem = SupplierDaoMem.getInstance();

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

    //TODO:
    private boolean isValidRequestParameters(HttpServletRequest req) {
        String supplierId = req.getParameter("supplier");
        if (supplierId != null && canConvertToInteger(supplierId)){
            Supplier supplier = supplierDaoMem.find(Integer.parseInt(supplierId));
            List<Product> productsBySupplier = productDataStore.getBy(supplier);
            return productsBySupplier.size() > 0;
        }

        String categoryId = req.getParameter("category");
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

        List<Product> products;
        List<ProductCategory> categories = productCategoryDataStore.getAll();
        List<Supplier> suppliers = supplierDaoMem.getAll();

        //TODO: move the following validation into method
        //It check which products must display and send to index

        String supplierId = req.getParameter("supplier");
        Supplier supplier;
        if (supplierId != null){
            supplier = supplierDaoMem.find(Integer.parseInt(supplierId));
            products = supplier.getProducts();
            context.setVariable("products", products);
        }

        String categoryId = req.getParameter("category");
        ProductCategory productCategory;
        if (categoryId != null){
            productCategory = productCategoryDataStore.find(Integer.parseInt(categoryId));
            context.setVariable("category", productCategory);
            products = productDataStore.getBy(productCategory);
            context.setVariable("products", products);
        }

        context.setVariable("suppliers", suppliers);
        context.setVariable("categories", categories);
    }
}