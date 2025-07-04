package ecommerce.models;

import java.time.LocalDate;

public class PerishableProduct extends Product {
    private LocalDate expirationDate;
    private boolean needsShipping;
    private double weight;

    public PerishableProduct(String name, double price, int quantity,
            LocalDate expirationDate, boolean needsShipping, double weight) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
        this.needsShipping = needsShipping;
        this.weight = weight;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    @Override
    public boolean requiresShipping() {
        return needsShipping;
    }

    public double getWeight() {
        return weight;
    }
}