package com.codecool.shop.dao.database;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoDB implements ProductCategoryDao {
    private static ProductCategoryDaoDB instance = null;
    private DataSource dataSource;

    private ProductCategoryDaoDB() {
        init();
    }

    public static ProductCategoryDaoDB getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoDB();
        }
        return instance;
    }

    private void init() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName(System.getenv("DATABASE"));
        dataSource.setUser(System.getenv("USERNAME"));
        dataSource.setPassword(System.getenv("PASSWORD"));
    }

    @Override
    public void add(ProductCategory category) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();

            String sql = "INSERT INTO category (department, name, description) " +
                         "VALUES (?,?,?)";
            statement = connection.prepareStatement(sql);

            statement.setString(1, category.getDepartment());
            statement.setString(2, category.getName());
            statement.setString(3, category.getDescription());

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
    public ProductCategory find(int id) {
        ProductCategory category = null;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();

            String sql =
                    "SELECT * FROM category " +
                            "WHERE id = ?";

            statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1,id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.first()) {
                String department = resultSet.getString("department");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                category = new ProductCategory(name, department, description);
                category.setId(id);
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
        return category;
    }

    @Override
    public void remove(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();

            String sql =
                    "DELETE FROM category " +
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
    public List<ProductCategory> getAll() {
        List<ProductCategory> categories = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();

            String sql = "SELECT * FROM category";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String department = resultSet.getString("department");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                ProductCategory category = new ProductCategory(name, department, description);
                category.setId(resultSet.getInt("id"));

                categories.add(category);
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
        return categories;
    }
}
