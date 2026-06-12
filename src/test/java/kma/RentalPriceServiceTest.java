package kma;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ua.edu.kma.RentalPriceService;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RentalPriceServiceTest {

    private RentalPriceService service;

    @BeforeEach
    void setUp() {
        service = new RentalPriceService();
    }

    @Test
    @Tag("fast")
    @DisplayName("Price calculation without discount for 3 days")
    void calculatePrice_ShouldReturnCorrectBasePrice() {
        double price = service.calculatePrice(3);
        assertEquals(150.0, price, "Price for 3 days should be 150.0");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    @Tag("validation")
    @DisplayName("Validation of non-negative number of days")
    void calculatePrice_ShouldThrowException_WhenDaysInvalid(int invalidDays) {
        assertThrows(IllegalArgumentException.class, () -> service.calculatePrice(invalidDays));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 50.0",
            "7, 350.0",
            "8, 360.0",
            "10, 450.0"
    })
    @Tag("fast")
    @DisplayName("Price calculation with discounts applied")
    void calculatePrice_ShouldApplyDiscountCorrectly(int days, double expectedPrice) {
        assertEquals(expectedPrice, service.calculatePrice(days));
    }

    @TestFactory
    @Tag("fast")
    @DisplayName("Dynamic check of available car classes")
    Stream<DynamicTest> testAvailableClasses() {
        return service.getAvailableClasses().stream()
                .map(carClass -> DynamicTest.dynamicTest(
                        "Car class " + carClass + " is valid",
                        () -> assertFalse(carClass.isEmpty())
                ));
    }

    @Test
    @Tag("fast")
    @DisplayName("Price calculation for Premium cars (if available in the car park)")
    void calculatePrice_ShouldRunOnlyIfPremiumAvailable() {
        Assumptions.assumeTrue(service.getAvailableClasses().contains("Premium"),
                "Test skipped: Premium class is currently removed from the car park");

        double expectedPrice = 100.0;
        double actualPrice = service.calculatePrice(2);

        assertEquals(expectedPrice, actualPrice, "Price was calculated incorrectly");
    }

}