package test;

import main.Calculator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Nested;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CalculatorTestsBasic {

    // In this File:

    // Lifecycle Methods
    // Basic Assertions
    // Display Names

    private Calculator calculator;

    // Lifecycle Methods:

    @BeforeAll
    static void setupAll() {
        System.out.println("Running setup before all tests...");
    }

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test executed. Cleaning up...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("All tests executed. Final cleanup...");
    }


    // Basic Test Functions using Assertions:

    @Test
    @DisplayName("Test Addition")
    void testAddition() {
        calculator.clear();
        calculator.appendNumber("5");
        calculator.setOperator('+');
        calculator.appendNumber("10");
        calculator.calculate();

        assertEquals("15.0", calculator.inputField.getText(), "Addition failed");

        assertNotNull(calculator, "Calculator object is null");
    }


    @Test
    @DisplayName("Test Subtraction")
    void testSubtraction() {
        calculator.clear();
        calculator.appendNumber("50");
        calculator.setOperator('-');
        calculator.appendNumber("25");
        calculator.calculate();

        assertEquals("25.0", calculator.inputField.getText());

        assertNotEquals("75.0", calculator.inputField.getText());
    }


    @Test
    @DisplayName("Test Multiplication")
    void testMultiplication() {
        calculator.clear();
        calculator.appendNumber("6");
        calculator.setOperator('*');
        calculator.appendNumber("7");
        calculator.calculate();

        // Dependent assertions: Only execute the second set if the first one passes
        assertAll("Dependent checks for multiplication result",
                () -> {
                    assertNotNull(calculator.inputField, "Input field is null");
                    assertTrue(calculator.inputField.getText().contains("42"), "Expected value not present");
                },
                () -> {
                    String result = calculator.inputField.getText();
                    assertTrue(result.startsWith("4"), "Result does not start with 4");
                }
        );
    }
}
