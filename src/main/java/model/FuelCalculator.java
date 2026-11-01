package model;

public class FuelCalculator {

    public double calculateFuel(double consumption, double distance) {
        return (consumption / 100) * distance;
    }

    public double calculateCost(double fuel, double pricePerLiter) {
        return fuel * pricePerLiter;
    }
}