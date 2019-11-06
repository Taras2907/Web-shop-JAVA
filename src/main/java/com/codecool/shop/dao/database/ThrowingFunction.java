package com.codecool.shop.dao.database;

import java.sql.SQLException;

@FunctionalInterface
public interface ThrowingFunction<F, S, R> {
    public R apply(F first, S second) throws SQLException;
}
