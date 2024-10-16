package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectivity {
    private static final String URL = "jdbc:mysql://localhost:3306/shop_smart";
    private static final String USER = "root";
    private static final String PASSWORD = "m#P52s@ap$V"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
