package ua.edu.kma;

import java.util.List;

public class RentalPriceService {
    private static final double BASE_PRICE_PER_DAY = 50.0;

    public double calculatePrice(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Days must be greater than 0");
        }
        double total = days * BASE_PRICE_PER_DAY;
        if (days > 7) {
            total *= 0.9;
        }
        return total;
    }

    public List<String> getAvailableClasses() {
        return List.of("Economy", "Premium", "SUV");
    }
}