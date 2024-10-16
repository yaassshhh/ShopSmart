package com.main;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.dao.ItemDAO;
import com.dao.OrderDAO;
import com.dao.UserDAO;
import com.entity.Item;
import com.entity.Order;
import com.entity.User;
import com.service.Review;
import com.service.ShoppingCart;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UserDAO userDAO = new UserDAO();
    private static ItemDAO itemDAO = new ItemDAO();
    private static OrderDAO orderDAO = new OrderDAO();
    private static ShoppingCart shoppingCart = new ShoppingCart(); 

    public static void main(String[] args) {
        boolean isRunning = true;
        
        while (isRunning) {
            showMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    browseItems();
                    break;
                case 4:
                    viewCart();
                    break;
                case 5:
                    placeOrder();
                    break;
                case 6:
                    leaveReview();
                    break;
                case 7:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Main Menu Display
    private static void showMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Browse Items");
        System.out.println("4. View Cart");
        System.out.println("5. Place Order");
        System.out.println("6. Leave a Review");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    // Option 1: Register User
    private static void registerUser() {
        System.out.println("\n=== Register ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User newUser = new User(0, username, email, password);

        try {
            userDAO.addUser(newUser);
            System.out.println("User registered successfully.");
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
        }
    }

    // Option 2: Login User
    private static void loginUser() {
        System.out.println("\n=== Login ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
        }
    }

    // Option 3: Browse Items
    private static void browseItems() {
        System.out.println("\n=== Browse Items ===");
        try {
            List<Item> items = itemDAO.getAllItems();
            if (items.isEmpty()) {
                System.out.println("No items available.");
            } else {
                for (Item item : items) {
                    System.out.println(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching items: " + e.getMessage());
        }
        
//        System.out.print("Enter the ID of the item to add to cart (or 0 to go back): ");
//        int itemId = scanner.nextInt();
//        if (itemId != 0) {
//            try {
//                Item item = itemDAO.getItemById(itemId);
//                if (item != null) {
//                    shoppingCart.addItem(item);
//                    System.out.println(item.getItemName() + " added to cart.");
//                } else {
//                    System.out.println("Invalid item ID.");
//                }
//            } catch (SQLException e) {
//                System.err.println("Error adding item to cart: " + e.getMessage());
//            }
//        }
    }
    
//     Option 4: View Cart
    private static void viewCart() {
        System.out.println("\n=== Your Cart ===");
        shoppingCart.listCartItems();

        System.out.print("Enter 1 to remove an item, 0 to go back: ");
        int choice = scanner.nextInt();
        if (choice == 1) {
            System.out.print("Enter the ID of the item to remove: ");
            int itemId = scanner.nextInt();
            boolean removed = shoppingCart.removeItemById(itemId);
            if (removed) {
                System.out.println("Item removed from cart.");
            } else {
                System.out.println("Item with ID " + itemId + " not found in cart.");
            }
        }
    }


    // Option 5: Place Order
    private static void placeOrder() {
        System.out.println("\n=== Place Order ===");
        List<Item> cartItems = shoppingCart.listCartItems();
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        String orderId = "ORD" + System.currentTimeMillis();
        double totalPrice = shoppingCart.calculateTotalPrice();
        
        Order order = new Order(orderId, cartItems);
        try {
            orderDAO.addOrder(order);
            System.out.println("Order placed successfully. Total price: $" + totalPrice);
            shoppingCart.clearCart();
        } catch (SQLException e) {
            System.err.println("Error placing order: " + e.getMessage());
        }
    }

    // Option 6: Leave a Review
    private static void leaveReview() {
        System.out.println("\n=== Leave a Review ===");
        System.out.print("Enter item ID: ");
        int itemId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter rating (1-5): ");
        int rating = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter comment: ");
        String comment = scanner.nextLine();

        Review review = new Review(rating, comment);
        try {
            itemDAO.addReview(itemId, review);
            System.out.println("Review submitted.");
        } catch (SQLException e) {
            System.err.println("Error submitting review: " + e.getMessage());
        }
    }
}
