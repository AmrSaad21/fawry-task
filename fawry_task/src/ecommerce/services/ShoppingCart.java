package ecommerce.services;

import ecommerce.models.CartItem;
import ecommerce.models.Product;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<CartItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void add(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock available for " + product.getName());
        }

        // Check if product already exists in cart
        for (CartItem item : items) {
            if (item.getProduct().getName().equals(product.getName())) {
                int totalQuantity = item.getQuantity() + quantity;
                if (totalQuantity > product.getQuantity()) {
                    throw new IllegalArgumentException("Not enough stock available for " + product.getName());
                }
                items.remove(item);
                items.add(new CartItem(product, totalQuantity));
                return;
            }
        }

        items.add(new CartItem(product, quantity));
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }
}