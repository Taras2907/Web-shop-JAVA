package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.function.Function;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);

        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);

        Supplier dell = new Supplier("Dell", "Computers");
        supplierDataStore.add(dell);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);

        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "A computer that is portable and suitable for use while travelling.");
        productCategoryDataStore.add(laptop);
        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 220.8f, "PLN", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad 700", 1684, "PLN", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Helpful technical support.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 367, "PLN", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.. Adjustable kickstand.", tablet, amazon));

        productDataStore.add(new Product("Dell Inspiron 5570", 2800, "PLN", "Dell Inspiron 5570 is a Windows 10 laptop. Core i7 processor. 4GB RAM. Packs 1TB of HDD storage.", laptop, dell));
        productDataStore.add(new Product("Lenovo IdeaPad 330", 1599, "PLN", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", laptop, lenovo));
        productDataStore.add(new Product("Dell XPS", 12700, "PLN", "Dell's XPS laptops are considered the gold standard when it comes to premium notebooks.", laptop, dell));
    }
}
