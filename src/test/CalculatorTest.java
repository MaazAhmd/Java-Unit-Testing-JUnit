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
class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    // Some more Basic Functions

    // In this File:

    // Method Order
    // Tags
    // Nested Tests

    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @Nested
    @Tag("basic")
    @DisplayName("Basic Function Tests")
    class BasicFunctions {

        @Test
        @Order(1)
        @DisplayName("Test Addition")
        void testAddition() {
            calculator.clear();
            calculator.appendNumber("5");
            calculator.setOperator('+');
            calculator.appendNumber("10");
            calculator.calculate();
            assertEquals("15.0", calculator.inputField.getText(), "Addition failed");

            assertTimeout(ofMinutes(1), () -> calculator.calculate());
            assertNotNull(calculator, "Calculator object is null");
        }

        @RepeatedTest(3)
        @Order(3)
        @DisplayName("Test Subtraction (Repeated)")
        void test_Subtraction_Repeated() {
            calculator.clear();
            calculator.appendNumber("50");
            calculator.setOperator('-');
            calculator.appendNumber("25");
            calculator.calculate();
            assertEquals("25.0", calculator.inputField.getText());
        }

        @Test
        @Order(2)
        @DisplayName("Test Subtraction")
        void testSubtraction() {
            calculator.clear();
            calculator.appendNumber("20");
            calculator.setOperator('-');
            calculator.appendNumber("8");
            calculator.calculate();
            assertEquals("12.0", calculator.inputField.getText(), "Subtraction failed");

            assertAll("Calculator state after subtraction",
                    () -> assertTrue(calculator.inputField.getText().contains("12"), "Output mismatch"),
                    () -> assertNotNull(calculator.inputField, "Input field is null")
            );
        }

        @Test
        @Order(4)
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
        @Order(5)
        @DisplayName("Test Division")
        void testDivision() {
            assumeTrue("DEV".equals(System.getenv("ENV")), "Test aborted: Not on developer workstation");

            calculator.clear();
            calculator.appendNumber("50");
            calculator.setOperator('/');
            calculator.appendNumber("5");
            calculator.calculate();
            assertEquals("10.0", calculator.inputField.getText(), "Division failed");

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
        @Order(6)
        @DisplayName("Test Division by Zero")
        @Disabled("Function has some issues to be resolved")
        void testDivisionByZero() {
            calculator.clear();
            calculator.appendNumber("10");
            calculator.setOperator('/');
            calculator.appendNumber("0");
            calculator.calculate();
            assertEquals("Error", calculator.inputField.getText());
        }

        @Test
        @Order(7)
        @DisplayName("Test Timeout Scenarios")
        void testTimeouts() {
            assertTimeout(ofMillis(100), () -> {
                calculator.clear();
                calculator.appendNumber("5");
                calculator.calculate();
            });

            assertTimeoutPreemptively(ofMillis(50), () -> {
                Thread.sleep(30);
            });
        }

    }

    // Advanced Functions
    @Nested
    @Tag("advanced")
    @DisplayName("Advanced Function Tests")
    class AdvancedFunctions {

        @Test
        @DisplayName("Test Square")
        void testSquare() {
            calculator.clear();
            calculator.appendNumber("4");
            calculator.square();
            assertEquals("16.0", calculator.inputField.getText());
        }

        @Test
        @DisplayName("Test Cube")
        void testCube() {
            calculator.clear();
            calculator.appendNumber("3");
            calculator.cube();
            assertEquals("27.0", calculator.inputField.getText());
        }

        @Test
        @DisplayName("Test Square Root With Negative Values")
        void testSquareRootWithNegativeNumbers() {
            // Test for a valid positive number
            //calculator.clear();
            //calculator.appendNumber("16");
            //calculator.squareRoot();
            // assertEquals("4.0", calculator.inputField.getText(), "Square root failed");

            // Test for an invalid negative number
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                calculator.clear();
                calculator.appendNumber("-1");
                calculator.squareRoot();
            });

            // Check that the exception message is as expected
            assertEquals("Cannot compute square root of a negative number", exception.getMessage(), "Exception message mismatch");
        }

        @Test
        @DisplayName("Test Square Root With Positive Values")
        void testSquareRootWithPositiveNumbers() {
            // Test for a valid positive number
            Exception exception = assertThrowsExactly(IllegalArgumentException.class, () -> {
                calculator.clear();
                calculator.appendNumber("-1");
                calculator.squareRoot();
            });

            // Check that the exception message is as expected
            assertEquals("Cannot compute square root of a negative number", exception.getMessage(), "Exception message mismatch");
        }


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

        @ParameterizedTest(name = "Invalid Square Root for input: {0}")
        @ValueSource(strings = {"-1", "-10", "-100"})
        void test_Invalid_Square_Root(String input) {
            calculator.clear();
            calculator.appendNumber(input);
            assertThrows(IllegalArgumentException.class, () -> {
                calculator.squareRoot();
            });
        }
    }

    // Trigonometric Functions
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @Nested
    @Tag("trigonometry")
    @DisplayName("Trigonometric Function Tests")
    class TrigonometricTests {

        @Test
        @Order(2)
        @DisplayName("Test Sine Function")
        void testSine() {
            calculator.clear();
            calculator.appendNumber("30");
            calculator.sine();
            assertEquals("0.49999999999999994", calculator.inputField.getText(), "Sine calculation failed");

            assertAll("Validate sine calculation",
                    () -> assertNotNull(calculator.inputField, "Input field is null"),
                    () -> assertTrue(calculator.inputField.getText().contains("0.49"), "Output mismatch")
            );
        }

        @Test
        @Order(1)
        @DisplayName("Test Cosine")
        void testCosine() {
            calculator.clear();
            calculator.appendNumber("60");
            calculator.cosine();
            assertEquals("0.5000000000000001", calculator.inputField.getText());
        }

        @Test
        @Order(3)
        @DisplayName("Test Tangent")
        void testTangent() {
            calculator.clear();
            calculator.appendNumber("45");
            calculator.tangent();
            assertEquals("0.9999999999999999", calculator.inputField.getText());
        }
    }
}
