package ecommerce.services;

import ecommerce.interfaces.Shippable;
import java.util.List;

public class ShippingService {
    private static final double SHIPPING_RATE_PER_KG = 15.0;

    public static double calculateShippingFee(List<Shippable> items) {
        if (items.isEmpty()) {
            return 0.0;
        }

        double totalWeight = items.stream().mapToDouble(Shippable::getWeight).sum();
        double shippingFee = totalWeight * SHIPPING_RATE_PER_KG;

        // Print shipment notice
        System.out.println("** Shipment notice **");
        for (Shippable item : items) {
            System.out.printf("1x %s %.0fg%n", item.getName(), item.getWeight() * 1000);
        }
        System.out.printf("Total package weight %.1fkg%n", totalWeight);

        return shippingFee;
    }
}