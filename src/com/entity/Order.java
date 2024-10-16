package com.entity;

import java.util.List;

public class Order {
    private String orderID;
    private List<Item> itemList;
    private double totalPrice;

    public Order(String orderID, List<Item> itemList) {
        this.orderID = orderID;
        this.itemList = itemList;
        this.totalPrice = calculateTotalPrice();
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
        this.totalPrice = calculateTotalPrice();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private double calculateTotalPrice() {
        return itemList.stream().mapToDouble(Item::getPrice).sum();
    }

    @Override
    public String toString() {
        return "Order{" + "orderID='" + orderID + '\'' + ", totalPrice=" + totalPrice + '}';
    }
}
