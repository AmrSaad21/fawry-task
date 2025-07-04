package ecommerce.main;

import ecommerce.models.*;
import ecommerce.services.*;
import java.time.LocalDate;

public class ECommerceDemo {
    public static void main(String[] args) {
        // Create products
        Product cheese = new PerishableProduct("Cheese", 100, 10,
                LocalDate.now().plusDays(7), true, 1); // 1kg
        Product biscuits = new PerishableProduct("Biscuits", 150, 5,
                LocalDate.now().plusDays(30), true, 0.2); // 200g
        Product tv = new NonPerishableProduct("TV", 1000, 3, true, 5.0); // 5kg
        Product scratchCard = new NonPerishableProduct("Mobile Scratch Card", 50, 20, false, 0);
        Product milk = new PerishableProduct("Milk", 10.99, 15,
                LocalDate.now(), true, 1.0); // Expires today, decimal price
        Product book = new NonPerishableProduct("Book", 29.99, 10, true, 0.5); // Decimal price and weight
        Product expiredCheese = new PerishableProduct("Expired Cheese", 100, 5,
                LocalDate.now().minusDays(1), true, 0.4);
        Product zeroWeightItem = new NonPerishableProduct("Poster", 5, 10, true, 0); // Shippable with zero weight

        // Create customers
        Customer customer = new Customer("Amr Saad 1", 2000);
        Customer poorCustomer = new Customer("Amr Saad 2", 100);
        Customer zeroBalanceCustomer = new Customer("Amr Saad 3", 0);
        Customer exactBalanceCustomer = new Customer("Amr Saad 4", 200); // For exact amount test

        // Test Case 1: Normal Checkout
        System.out.println("=== Test Case 1: Normal Checkout ===");
        ShoppingCart cart1 = new ShoppingCart();
        try {
            cart1.add(cheese, 2);
            cart1.add(biscuits, 1);
            cart1.add(scratchCard, 1);
            ECommerceSystem.checkout(customer, cart1);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test Case 2: Empty Cart
        System.out.println("\n=== Test Case 2: Empty Cart ===");
        ShoppingCart emptyCart = new ShoppingCart();
        ECommerceSystem.checkout(customer, emptyCart);

        // Test Case 3: Insufficient Balance
        System.out.println("\n=== Test Case 3: Insufficient Balance ===");
        ShoppingCart expensiveCart = new ShoppingCart();
        expensiveCart.add(tv, 2);
        ECommerceSystem.checkout(poorCustomer, expensiveCart);

        // Test Case 4: Out of Stock
        System.out.println("\n=== Test Case 4: Out of Stock ===");
        ShoppingCart bigCart = new ShoppingCart();
        try {
            bigCart.add(cheese, 20); // Only 8 left after first purchase
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test Case 5: Expired Product
        System.out.println("\n=== Test Case 5: Expired Product ===");
        ShoppingCart expiredCart = new ShoppingCart();
        expiredCart.add(expiredCheese, 1);
        ECommerceSystem.checkout(customer, expiredCart);

        // Test Case 6: No Shipping Required
        System.out.println("\n=== Test Case 6: No Shipping Required ===");
        ShoppingCart digitalCart = new ShoppingCart();
        digitalCart.add(scratchCard, 3);
        ECommerceSystem.checkout(customer, digitalCart);

        // Test Case 7: Zero Quantity
        System.out.println("\n=== Test Case 7: Zero Quantity ===");
        ShoppingCart zeroQtyCart = new ShoppingCart();
        try {
            zeroQtyCart.add(cheese, 0);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test Case 8: Negative Quantity
        System.out.println("\n=== Test Case 8: Negative Quantity ===");
        ShoppingCart negQtyCart = new ShoppingCart();
        try {
            negQtyCart.add(cheese, -1);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test Case 9: Maximum Quantity
        System.out.println("\n=== Test Case 9: Maximum Quantity ===");
        ShoppingCart maxQtyCart = new ShoppingCart();
        try {
            maxQtyCart.add(scratchCard, 20); // Max available
            ECommerceSystem.checkout(customer, maxQtyCart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test Case 10: Multiple Additions of Same Product
        System.out.println("\n=== Test Case 10: Multiple Additions of Same Product ===");
        ShoppingCart multiAddCart = new ShoppingCart();
        try {
            multiAddCart.add(cheese, 1);
            multiAddCart.add(cheese, 2); // Should accumulate to 3
            ECommerceSystem.checkout(customer, multiAddCart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test Case 11: Perishable Product About to Expire
        System.out.println("\n=== Test Case 11: Perishable Product About to Expire ===");
        ShoppingCart expireTodayCart = new ShoppingCart();
        expireTodayCart.add(milk, 1);
        ECommerceSystem.checkout(customer, expireTodayCart);

        // Test Case 12: Customer Balance After Multiple Purchases
        System.out.println("\n=== Test Case 12: Customer Balance After Multiple Purchases ===");
        ShoppingCart multiPurchaseCart = new ShoppingCart();
        multiPurchaseCart.add(book, 2);
        ECommerceSystem.checkout(customer, multiPurchaseCart);

        // Test Case 13: Large Order
        System.out.println("\n=== Test Case 13: Large Order ===");
        ShoppingCart largeCart = new ShoppingCart();
        try {
            largeCart.add(scratchCard, 10);
            largeCart.add(book, 5);
            largeCart.add(milk, 10);
            ECommerceSystem.checkout(customer, largeCart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test Case 14: Product with Zero Weight
        System.out.println("\n=== Test Case 14: Product with Zero Weight ===");
        ShoppingCart zeroWeightCart = new ShoppingCart();
        zeroWeightCart.add(zeroWeightItem, 2);
        ECommerceSystem.checkout(customer, zeroWeightCart);

        // Test Case 15: Customer with Zero Balance
        System.out.println("\n=== Test Case 15: Customer with Zero Balance ===");
        ShoppingCart zeroBalCart = new ShoppingCart();
        zeroBalCart.add(book, 1);
        ECommerceSystem.checkout(zeroBalanceCustomer, zeroBalCart);

        // Test Case 16: Adding Product After Out of Stock
        System.out.println("\n=== Test Case 16: Adding Product After Out of Stock ===");
        ShoppingCart outOfStockCart = new ShoppingCart();
        try {
            outOfStockCart.add(biscuits, 5); // Max 5 available
            ECommerceSystem.checkout(customer, outOfStockCart);
            ShoppingCart postStockCart = new ShoppingCart();
            postStockCart.add(biscuits, 1); // Should fail
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}