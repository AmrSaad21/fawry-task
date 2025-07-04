package ecommerce.models;

public class NonPerishableProduct extends Product {
    private boolean needsShipping;
    private double weight;

    public NonPerishableProduct(String name, double price, int quantity,
            boolean needsShipping, double weight) {
        super(name, price, quantity);
        this.needsShipping = needsShipping;
        this.weight = weight;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public boolean requiresShipping() {
        return needsShipping;
    }

    public double getWeight() {
        return weight;
    }
}