package com.codecool.shop.dao.database;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoDB implements SupplierDao {
    private static SupplierDaoDB instance = null;
    private DataSource dataSource;

    private SupplierDaoDB() {
        init();
    }

    private void init() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName(System.getenv("DATABASE"));
        dataSource.setUser(System.getenv("USERNAME"));
        dataSource.setPassword(System.getenv("PASSWORD"));
    }

    public static SupplierDaoDB getInstance() {
        if (instance == null) {
            instance = new SupplierDaoDB();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();

            String sql = "INSERT INTO supplier (name, description) " +
                    "VALUES (?,?)";
            statement = connection.prepareStatement(sql);

            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getDescription());

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
    public Supplier find(int id) {
        Supplier supplier = null;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();

            String sql =
                    "SELECT * FROM supplier " +
                            "WHERE id = ?";

            statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1,id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.first()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                supplier = new Supplier(name, description);
                supplier.setId(id);
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
        return supplier;
    }

    @Override
    public void remove(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();

            String sql =
                    "DELETE FROM supplier " +
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
    public List<Supplier> getAll() {
        List<Supplier> suppliers = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();

            String sql = "SELECT * FROM supplier";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                Supplier supplier = new Supplier(name, description);
                supplier.setId(resultSet.getInt("id"));

                suppliers.add(supplier);
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
        return suppliers;
    }
}
