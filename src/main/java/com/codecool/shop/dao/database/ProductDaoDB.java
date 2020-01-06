package com.codecool.shop.dao.database;

import com.codecool.shop.dao.Dao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoDB implements Dao<Product> {
    private static ProductDaoDB instance = null;
    private DataSource dataSource;
    private ProductCategoryDaoDB categoryDao = ProductCategoryDaoDB.getInstance();
    private SupplierDaoDB supplierDao = SupplierDaoDB.getInstance();

    public static ProductDaoDB getInstance() {
        if (instance == null) {
            instance = new ProductDaoDB();
        }
        return instance;
    }


    private ProductDaoDB() {
        dataSource = init();
    }

    private DataSource init() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName(System.getenv("DATABASE"));
        dataSource.setUser(System.getenv("USERNAME"));
        dataSource.setPassword(System.getenv("PASSWORD"));
        return dataSource;
    }

    @Override
    public void add(Product product) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            Supplier supplier = product.getSupplier();
            ProductCategory category = product.getProductCategory();

            connection = dataSource.getConnection();

            String sql = "INSERT INTO product (default_price, default_currency, supplier_id, category_id, name, description) " +
                    "VALUES (?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);

            statement.setFloat(1, product.getDefaultPrice());
            statement.setString(2, product.getDefaultCurrency().toString());
            statement.setInt(3, supplier.getId());
            statement.setInt(4, category.getId());
            statement.setString(5, product.getName());
            statement.setString(6, product.getDescription());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Product find(int id) {
        Product product = null;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();

            String sql =
                    "SELECT * FROM product " +
                            "WHERE id = ?";

            statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1,id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.first()) {
                String name = resultSet.getString("name");
                float default_price = resultSet.getFloat("default_price");
                String currency = resultSet.getString("default_currency");
                String description = resultSet.getString("description");
                int supplier_id = resultSet.getInt("supplier_id");
                int category_id = resultSet.getInt("category_id");

                Supplier supplier = supplierDao.find(supplier_id);
                ProductCategory category = categoryDao.find(category_id);

                product = new Product(name, default_price, currency, description, category, supplier);
                product.setId(id);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return product;

    }

    @Override
    public void remove(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();

            String sql =
                    "DELETE FROM product " +
                            "WHERE id = ?";
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();

            String sql = "SELECT * FROM product";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                float default_price = resultSet.getFloat("default_price");
                String currency = resultSet.getString("default_currency");
                String description = resultSet.getString("description");
                int supplier_id = resultSet.getInt("supplier_id");
                int category_id = resultSet.getInt("category_id");

                Supplier supplier = supplierDao.find(supplier_id);
                ProductCategory category = categoryDao.find(category_id);

                Product product = new Product(name, default_price, currency, description, category, supplier);
                product.setId(resultSet.getInt("id"));

                products.add(product);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }

    public List<Product> getBy(Supplier supplier) {
        List<Product> products = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();

            String sql = "SELECT * FROM product " +
                    "WHERE supplier_id = ?";

            statement = connection.prepareStatement(sql);

            statement.setInt(1, supplier.getId());

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                float default_price = resultSet.getFloat("default_price");
                String currency = resultSet.getString("default_currency");
                String description = resultSet.getString("description");
                int category_id = resultSet.getInt("category_id");

                ProductCategory category = categoryDao.find(category_id);

                Product product = new Product(name, default_price, currency, description, category, supplier);
                product.setId(resultSet.getInt("id"));

                products.add(product);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }


    public List<Product> getBy(ProductCategory productCategory) {
        List<Product> products = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();

            String sql = "SELECT * FROM product " +
                    "WHERE category_id = ?";

            statement = connection.prepareStatement(sql);

            statement.setInt(1, productCategory.getId());

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                float default_price = resultSet.getFloat("default_price");
                String currency = resultSet.getString("default_currency");
                String description = resultSet.getString("description");
                int supplier_id = resultSet.getInt("supplier_id");

                Supplier supplier = supplierDao.find(supplier_id);

                Product product = new Product(name, default_price, currency, description, productCategory, supplier);
                product.setId(resultSet.getInt("id"));

                products.add(product);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }
}
