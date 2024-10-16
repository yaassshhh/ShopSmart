package com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.entity.Item;
import com.entity.Order;
import com.util.DatabaseConnectivity;

public class OrderDAO {

    public void addOrder(Order order) throws SQLException {
        String itemIds = order.getItemList().stream()
                              .map(item -> String.valueOf(item.getId())) 
                              .collect(Collectors.joining(","));
                              
        String sql = "INSERT INTO orders (order_id, total_price, item_ids) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, order.getOrderID());
            statement.setDouble(2, order.getTotalPrice());
            statement.setString(3, itemIds);
            statement.executeUpdate();
        }
    }

    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection connection = DatabaseConnectivity.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String[] itemIds = resultSet.getString("item_ids").split(",");
                List<Item> items = fetchItemsByIds(itemIds); 
                
                Order order = new Order(
                    resultSet.getString("order_id"),
                    items
                );
                orders.add(order);
            }
        }
        return orders;
    }

    //to fetch items by their IDs
    private List<Item> fetchItemsByIds(String[] itemIds) throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE id = ?";
        try (Connection connection = DatabaseConnectivity.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (String id : itemIds) {
                statement.setInt(1, Integer.parseInt(id));
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Item item = new Item(
                        resultSet.getString("item_name"),
                        resultSet.getDouble("price"),
                        resultSet.getString("category"), 0
                    );
                    items.add(item);
                }
            }
        }
        return items;
    }
}

