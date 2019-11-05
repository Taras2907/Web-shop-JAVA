package com.codecool.shop.tests;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoMemTest {
    @Test
    void checkInstance(){
        ProductCategoryDao productCategory = ProductCategoryDaoMem.getInstance();

    }
}