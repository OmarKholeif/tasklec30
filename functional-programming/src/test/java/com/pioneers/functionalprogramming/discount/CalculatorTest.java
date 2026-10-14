package com.pioneers.functionalprogramming.discount;

import com.pioneers.functionalprogramming.discount.utils.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CalculatorTest {

    private static Calculator calculator;

    /*static {
        log.info("I am in the setup");
        calculator = new Calculator();
    }*/

    @BeforeAll
    static void setup() {
        log.info("I am in the setup");
        calculator = new Calculator();
    }

    @AfterAll
    static void finish() {
        log.info("Finished");
    }

    /*@BeforeEach
    void init() {
        log.info("I am in the init");
        calculator = new Calculator();
    }*/

    @AfterEach
    void shutdown() {
        log.info("Finished the test case");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Mostafa", "Elsayed", "Omar"})
    void assertNamesNotNull(String name) {
        assertNotNull(name);
        assertTrue(!name.isBlank());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 3, 4",
            "-1, -3, -4",
            "0, 3, 3",
            "3, 0, 3",
            "0, -3, -3",
            "-3, 0, -3",
            "0, 0, 0"
    })
    void testAddNumbers(int num1, int num2, int expectedValue) {
        // Action
        final int actualValue = calculator.add(num1, num2);

        // Assertion
        assertEquals(expectedValue, actualValue);
    }

    /*@Test
    @Disabled("Temporary Ignored")
    @DisplayName("Two Positive Numbers")
    void testAddTwoPositiveNumbers() {
        // Arrange

        // Act
        final int actualValue = calculator.add(1, 3);

        // Assertion
        assertEquals(4, actualValue);
    }

    @Test
    @RepeatedTest(2)
    @DisplayName("Two Negative Numbers")
    void testAddTwoNegativeNumbers() {
        // Arrange

        // Ack
        final int actualValue = calculator.add(-1, -3);

        // Assertion
        assertEquals(-4, actualValue);
    }

    @Test
    void testAddZeroWithPositiveNumbers() {
        // Arrange

        // Ack
        final int actualValue = calculator.add(0, 3);

        // Assertion
        assertEquals(3, actualValue);
    }*/

    @ParameterizedTest
    @CsvSource({
            "1, 3, -2",
            "-1, -3, 2",
            "0, 3, -3",
            "3, 0, 3",
            "0, -3, 3",
            "-3, 0, -3",
            "0, 0, 0"
    })
    void testSubtractNumbers(int num1, int num2, int expectedValue) {
        // Action
        final int actualValue = calculator.subtract(num1, num2);

        // Assertion
        assertEquals(expectedValue, actualValue);
    }

    @ParameterizedTest
    @EnumSource(value = Category.class, names = {"ELECTRONICS", "CLOTHES"}, mode = EnumSource.Mode.EXCLUDE)
    void testCategory(Category category) {
        assertNotNull(category);
    }

    /*@Test
    void testSubtractTwoPositiveNumbers() {
        // Arrange

        // Ack
        final int actualValue = calculator.subtract(1, 3);

        // Assertion
        assertEquals(-2, actualValue);
    }

    @Test
    void testSubtractTwoNegativeNumbers() {
        // Arrange

        // Ack
        final int actualValue = calculator.subtract(-1, -3);

        // Assertion
        assertEquals(2, actualValue);
    }

    @Test
    void testSubtractZeroWithPositiveNumbers() {
        // Arrange

        // Ack
        final int actualValue = calculator.subtract(0, 3);

        // Assertion
        assertEquals(-3, actualValue);
    }*/
}
