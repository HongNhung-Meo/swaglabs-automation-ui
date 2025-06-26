package untils;

public class PriceUntils {
    public static boolean isTotalCorrect(double itemTotal, double tax, double totalDisplayed) {
        double calculated = itemTotal + tax;
        return Math.abs(calculated - totalDisplayed) < 0.01;
    }

    public static double calculateItemTotal(java.util.List<Double> prices) {
        return prices.stream().mapToDouble(Double::doubleValue).sum();
    }
}