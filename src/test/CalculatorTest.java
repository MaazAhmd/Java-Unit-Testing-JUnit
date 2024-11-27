package test;

import main.Calculator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.CountDownLatch;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CalculatorTest {

    private Calculator calculator;

    // Lifecycle Methods
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

    @Test
    @DisplayName("Test Addition")
    void testAddition() {
        calculator.clear();
        calculator.appendNumber("5");
        calculator.setOperator('+');
        calculator.appendNumber("10");
        calculator.calculate();

        // Basic assertion to check the result
        assertEquals("15.0", calculator.inputField.getText(), "Addition failed");

        // Example of timeout to ensure the method finishes in time
        assertTimeout(ofMinutes(1), () -> {
            calculator.calculate();
        });

        // Ensures the calculator object is not null
        assertNotNull(calculator, "Calculator object is null");
    }


    @RepeatedTest(3)
        // Repeats the test 3 times
    void test_Subtraction_Repeated() {
        calculator.clear();
        calculator.appendNumber("50");
        calculator.setOperator('-');
        calculator.appendNumber("25");
        calculator.calculate();
        assertEquals("25.0", calculator.inputField.getText());
    }


    @Test
    @DisplayName("Test Subtraction")
    void testSubtraction() {
        calculator.clear();
        calculator.appendNumber("20");
        calculator.setOperator('-');
        calculator.appendNumber("8");
        calculator.calculate();

        // Check if the result is as expected
        assertEquals("12.0", calculator.inputField.getText(), "Subtraction failed");

        // Ensure grouped assertions to validate calculator behavior
        assertAll("Calculator state after subtraction",
                () -> assertTrue(calculator.inputField.getText().contains("12"), "Output mismatch"),
                () -> assertNotNull(calculator.inputField, "Input field is null")
        );
    }


    @Test
    @DisplayName("Test Multiplication")
    void testMultiplication() {
        calculator.clear();
        calculator.appendNumber("6");
        calculator.setOperator('*');
        calculator.appendNumber("7");
        calculator.calculate();

        // Check the result
        assertEquals("42.0", calculator.inputField.getText(), "Multiplication failed");

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

    @Test
    @Order(1)
    @DisplayName("Test Division")
    void testDivision() {

        // Assumption: Test Will not run if assumption is False:
        assumeTrue("DEV".equals(System.getenv("ENV")), "Test aborted: Not on developer workstation");

        calculator.clear();
        calculator.appendNumber("50");
        calculator.setOperator('/');
        calculator.appendNumber("5");
        calculator.calculate();

        // Validate division
        assertEquals("10.0", calculator.inputField.getText(), "Division failed");

        // Test exception for invalid operations (division by zero)
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            calculator.clear();
            calculator.appendNumber("10");
            calculator.setOperator('/');
            calculator.appendNumber("0");
            calculator.calculate();
        });
        assertEquals("0", calculator.inputField.getText(), "Division by zero not handled correctly");
    }

    @Test
    @Order(2)
    @DisplayName("Test Division by Zero")
    void testDivisionByZero() {
        calculator.clear();
        calculator.appendNumber("10");
        calculator.setOperator('/');
        calculator.appendNumber("0");
        calculator.calculate();
        assertEquals("Error", calculator.inputField.getText());
    }


    @Test
    @DisplayName("Test Timeout Scenarios")
    void testTimeouts() {
        // Ensure method executes within a reasonable time (within 100 ms)
        assertTimeout(ofMillis(100), () -> {
            calculator.clear();
            calculator.appendNumber("5");
            calculator.calculate();
        });

        // Preemptively terminate tasks taking too long (this part should complete within 50 ms)
        assertTimeoutPreemptively(ofMillis(50), () -> {
            Thread.sleep(30); // Example: Sleep for a reasonable time less than 50ms
        });
    }

    // ParameterizedTest for testing invalid inputs in square root
    @ParameterizedTest(name = "Invalid Square Root for input: {0}")
    @ValueSource(strings = {"-1", "-10", "-100"})
    void test_Invalid_Square_Root(String input) {
        // Clear the calculator and set the invalid input
        calculator.clear();
        calculator.appendNumber(input);

        // Assert that an IllegalArgumentException is thrown for invalid square root
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.squareRoot();
        });

        // Ensure that the "Error" message is displayed on the calculator for invalid input
        //assertEquals("Error", calculator.inputField.getText(), "Error message not displayed for invalid square root");
    }

    @Test
    @Order(3)
    @DisplayName("Test Square")
    void testSquare() {
        calculator.clear();
        calculator.appendNumber("4");
        calculator.square();
        assertEquals("16.0", calculator.inputField.getText());
    }

    @Test
    @Order(4)
    @DisplayName("Test Cube")
    void testCube() {
        calculator.clear();
        calculator.appendNumber("3");
        calculator.cube();
        assertEquals("27.0", calculator.inputField.getText());
    }

    @Test
    @Order(5)
    @DisplayName("Test Square Root")
    void testSquareRoot() {
        calculator.clear();
        calculator.appendNumber("16");
        calculator.squareRoot();

        // Validate square root calculation
        assertEquals("4.0", calculator.inputField.getText(), "Square root failed");

        // Invalid input test for negative numbers
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.clear();
            calculator.appendNumber("-1");
            calculator.squareRoot();
        });
        assertEquals("NaN", calculator.inputField.getText(), "Negative input not handled correctly");
    }

    @Test
    @Order(6)
    @DisplayName("Test Sine Function")
    void testSine() {
        calculator.clear();
        calculator.appendNumber("30");
        calculator.sine();

        // Validate sine value
        assertEquals("0.49999999999999994", calculator.inputField.getText(), "Sine calculation failed");

        // Grouped assertions for additional checks
        assertAll("Validate sine calculation",
                () -> assertNotNull(calculator.inputField, "Input field is null"),
                () -> assertTrue(calculator.inputField.getText().contains("0.49"), "Output mismatch")
        );
    }

    @Test
    @Order(7)
    @DisplayName("Test Cosine")
    void testCosine() {
        calculator.clear();
        calculator.appendNumber("60");
        calculator.cosine();
        assertEquals("0.5000000000000001", calculator.inputField.getText());
    }

    @Test
    @Order(8)
    @DisplayName("Test Tangent")
    void testTangent() {
        calculator.clear();
        calculator.appendNumber("45");
        calculator.tangent();
        assertEquals("0.9999999999999999", calculator.inputField.getText());
    }

    // Test Logarithm with Parameterized Values
//    @ParameterizedTest(name = "Logarithm of {0} is {1}")
//    @CsvSource({
//            "1000, 3.0",
//            "100, 2.0",
//            "10, 1.0"
//    })

    @Test
    @DisplayName("Test Logarithm")
    void testLogarithm() {
        calculator.clear();
        calculator.appendNumber("1000");
        calculator.logarithm();
        assertEquals("3.0", calculator.inputField.getText());
    }

    @Test
    @DisplayName("Test Natural Logarithm")
    void testNaturalLogarithm() {
        calculator.clear();
        calculator.appendNumber("2.718281828459045");
        calculator.naturalLogarithm();
        assertEquals("1.0", calculator.inputField.getText());
    }

    @Test
    @DisplayName("Test Power")
    void testPower() {
        calculator.clear();
        calculator.appendNumber("2");
        calculator.setOperator('^');
        calculator.appendNumber("3");
        calculator.calculate();
        assertEquals("8.0", calculator.inputField.getText());
    }

    @Test
    @DisplayName("Test Clear")
    void testClear() {
        calculator.clear();
        calculator.appendNumber("123");
        calculator.clear();
        assertEquals("", calculator.inputField.getText());
    }

    @Test
    @DisplayName("Test Test Append Number")
    void testAppendNumber() {
        calculator.clear();
        calculator.appendNumber("5");
        calculator.appendNumber("6");
        assertEquals("56", calculator.inputField.getText());
    }

//    @Test
//    @DisplayName("Test Invalid Inputs")
//    void testInvalidInputs() {
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> calculator.sqrt(-1), "Negative square root should throw");
//        assertEquals("Invalid input for square root", exception.getMessage(), "Unexpected exception message");
//    }
}
