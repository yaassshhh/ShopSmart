package com.entity;

import com.exception.InvalidPriceException;

public class Item {
	private int id;
    private String itemName;
    private double price;
    private String category;

    

	public Item(String itemName, double price, String category, int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
    	this.itemName = itemName;
        this.price = price;
        this.category = category;
	}

	public Item(int int1, String string, double double1, String string2) {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price <= 0) {
            throw new InvalidPriceException("Price must be positive");
        }
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + "itemName='" + itemName + '\'' + ", price=" + price + ", category='" + category + '\'' + '}';
    }
}

