package ecommerce.models;

import ecommerce.interfaces.Shippable;

public class ShippableItem implements Shippable {
    private String name;
    private double weight;

    public ShippableItem(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}