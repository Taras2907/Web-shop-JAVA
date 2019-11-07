package com.codecool.shop.dao.database;

import com.codecool.shop.dao.Dao;
import com.codecool.shop.dao.implementation.localMemory.OrderDaoMem;
import com.codecool.shop.model.*;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDaoDB  implements Dao<User> {

    private static UserDaoDB instance = null;
    private DataSource dataSource;

    private UserDaoDB() {
       dataSource = init();
    }

    private DataSource init() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName(System.getenv("DATABASE"));
        dataSource.setUser(System.getenv("USERNAME"));
        dataSource.setPassword(System.getenv("PASSWORD"));
        return dataSource ;
    }

    public static UserDaoDB getInstance() {
        if (instance == null) {
            instance = new UserDaoDB();
        }
        return instance;
    }


    @Override
    public void add(User user) {


    }

    @Override
    public User find(int id) {
        User user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            String query = "SELECT * FROM \"user\" WHERE id=?";
            statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                int  orderId = resultSet.getInt("order_id");
                int  userId = resultSet.getInt("id");


                Order order = OrderDaoMem.getInstance().find(orderId);


                user = new User(userId, email, password, orderId, name);
                user.setId(id);
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
        return user;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }


    public User findByEmail(String email) {
        User user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            String query = "SELECT * FROM \"user\" WHERE email=?";
            statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1,email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                String name = resultSet.getString("name");
                String emailDb = resultSet.getString("email");
                String password = resultSet.getString("password");
                int  orderId = resultSet.getInt("order_id");
                int  userId = resultSet.getInt("id");


                Order order = OrderDaoMem.getInstance().find(orderId);


                user = new User(userId, emailDb, password, orderId, name);
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
        return user;
    }
}
