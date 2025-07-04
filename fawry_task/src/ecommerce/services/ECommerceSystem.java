package ecommerce.services;

import ecommerce.interfaces.Shippable;
import ecommerce.models.*;
import java.util.ArrayList;
import java.util.List;

public class ECommerceSystem {

    public static void checkout(Customer customer, ShoppingCart cart) {
        // Check if cart is empty
        if (cart.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }

        // Check for expired products and stock availability
        List<Shippable> shippableItems = new ArrayList<>();

        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();

            // Check if product is expired
            if (product.isExpired()) {
                System.out.println("  Product " + product.getName() + " is expired");
                return;
            }

            // Check if product is out of stock
            if (item.getQuantity() > product.getQuantity()) {
                System.out.println("  Product " + product.getName() + " is out of stock");
                return;
            }

            // Collect shippable items
            if (product.requiresShipping()) {
                for (int i = 0; i < item.getQuantity(); i++) {
                    double weight = 0;
                    if (product instanceof PerishableProduct) {
                        weight = ((PerishableProduct) product).getWeight();
                    } else if (product instanceof NonPerishableProduct) {
                        weight = ((NonPerishableProduct) product).getWeight();
                    }
                    shippableItems.add(new ShippableItem(product.getName(), weight));
                }
            }
        }

        // Calculate costs
        double subtotal = cart.getSubtotal();
        double shippingFee = ShippingService.calculateShippingFee(shippableItems);
        double totalAmount = subtotal + shippingFee;

        // Check customer balance
        if (customer.getBalance() < totalAmount) {
            System.out.println("Customer's balance is insufficient");
            return;
        }

        // Process payment and update inventory
        customer.deductBalance(totalAmount);
        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }

        // Print checkout receipt
        System.out.println("** Checkout receipt **");
        for (CartItem item : cart.getItems()) {
            System.out.printf("%dx %s %.0f%n",
                    item.getQuantity(),
                    item.getProduct().getName(),
                    item.getTotalPrice());
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f%n", subtotal);
        System.out.printf("Shipping %.0f%n", shippingFee);
        System.out.printf("Amount %.0f%n", totalAmount);
        System.out.printf("Customer balance after payment: %.0f%n", customer.getBalance());
    }
}