package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FuelCalculatorTest {

    private FuelCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new FuelCalculator();
    }

    @Test
    void fuelFromAssignmentExample() {
        assertEquals(11.7, calculator.calculateFuel(6.5, 180), 0.001);
    }

    @Test
    void costFromAssignmentExample() {
        double fuel = calculator.calculateFuel(6.5, 180);
        assertEquals(23.985, calculator.calculateCost(fuel, 2.05), 0.001);
    }

    @Test
    void zeroDistanceNeedsNoFuel() {
        assertEquals(0, calculator.calculateFuel(6.5, 0), 0.001);
    }

    @Test
    void zeroConsumptionNeedsNoFuel() {
        assertEquals(0, calculator.calculateFuel(0, 180), 0.001);
    }

    @Test
    void hundredKilometresUsesExactlyTheConsumption() {
        assertEquals(7.5, calculator.calculateFuel(7.5, 100), 0.001);
    }

    @Test
    void costIsZeroWhenFuelIsFree() {
        assertEquals(0, calculator.calculateCost(11.7, 0), 0.001);
    }

    @Test
    void longTripScalesLinearly() {
        double shortTrip = calculator.calculateFuel(8.0, 100);
        double longTrip = calculator.calculateFuel(8.0, 1000);
        assertEquals(shortTrip * 10, longTrip, 0.001);
    }
}