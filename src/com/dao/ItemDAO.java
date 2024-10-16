package com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.entity.Item;
import com.service.Review;
import com.util.DatabaseConnectivity;

public class ItemDAO {

    public void addItem(Item item) throws SQLException {
        String sql = "INSERT INTO items (item_name, price, category) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getItemName());
            statement.setDouble(2, item.getPrice());
            statement.setString(3, item.getCategory());
            statement.executeUpdate();
        }
    }

    public void updateItem(int itemId, Item item) throws SQLException {
        String sql = "UPDATE items SET item_name = ?, price = ?, category = ? WHERE id = ?";
        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getItemName());
            statement.setDouble(2, item.getPrice());
            statement.setString(3, item.getCategory());
            statement.setInt(4, itemId);
            statement.executeUpdate();
        }
    }

    public void deleteItem(int itemId) throws SQLException {
        String sql = "DELETE FROM items WHERE id = ?";
        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, itemId);
            statement.executeUpdate();
        }
    }

    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";
        try (Connection connection = DatabaseConnectivity.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Item item = new Item(
                    resultSet.getString("item_name"),
                    resultSet.getDouble("price"),
                    resultSet.getString("category"),0
                );
                items.add(item);
            }
        }
        return items;
    }

    public Item getItemById(int itemId) throws SQLException {
        String sql = "SELECT * FROM items WHERE id = ?";
        Item item = null;

        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, itemId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                item = new Item(
                    resultSet.getInt("id"),  
                    resultSet.getString("item_name"), 
                    resultSet.getDouble("price"),  
                    resultSet.getString("category")  
                );
            }
        }
        return item;
    }


	public void addReview(int itemId, Review review) throws SQLException {
	    String sql = "INSERT INTO reviews (user_id, item_id, rating, comment) VALUES (?, ?, ?, ?)";
	    
	    try (Connection connection = DatabaseConnectivity.getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {
	        
	        
	        int userId = review.getUserId();
	       
	        statement.setInt(1, userId); 
	        statement.setInt(2, itemId); 
	        statement.setInt(3, review.getRating());  
	        statement.setString(4, review.getComment());  
	        
	        statement.executeUpdate();
	    }
	}

}

