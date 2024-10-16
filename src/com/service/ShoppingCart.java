package com.service;

import java.util.ArrayList;
import java.util.List;

import com.entity.Item;

public class ShoppingCart {
    private List<Item> cartItems = new ArrayList<>();

    public void addItem(Item item) {
        cartItems.add(item);
    }

    public void removeItem(Item item) {
        cartItems.remove(item);
    }

    public boolean removeItemById(int itemId) {
        for (Item item : cartItems) {
            if (item.getId() == itemId) {
                cartItems.remove(item);
                return true; 
            }
        }
        return false; 
    }

    public List<Item> listCartItems() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Items in your cart:");
            for (Item item : cartItems) {
                System.out.println(item); 
            }
        }
        return cartItems;
    }


    public void clearCart() {
        cartItems.clear();
    }
	
    public double calculateTotalPrice() {
        double total = 0.0;  
        for (Item item : cartItems) {
            total += item.getPrice();  
        }
        return total;  
    }

}
