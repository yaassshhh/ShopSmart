package com.dao;

import java.sql.*;

import com.entity.User;
import com.util.DatabaseConnectivity;

public class UserDAO {

    
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        
        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set parameters for the query
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());

            
            statement.executeUpdate();
        }
    }

    
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;
        
        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username); 

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                
                user = new User(
                    resultSet.getInt("id"),  
                    resultSet.getString("username"),  
                    resultSet.getString("email"),  
                    resultSet.getString("password")  
                );
            }
        }
        return user;
    }


}

