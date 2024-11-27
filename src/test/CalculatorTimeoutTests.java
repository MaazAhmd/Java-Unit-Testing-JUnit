package test;

import main.Calculator;
import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTimeoutTests {

    private final Calculator calculator = new Calculator();

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testSquareRoot_WithinTimeout() throws InterruptedException {
        calculator.clear();
        calculator.appendNumber("16");
        calculator.squareRoot();

        // Validate square root calculation
        assertEquals("4.0", calculator.inputField.getText(), "Square root failed");

    }

    @RepeatedTest(3)
    @Timeout(value = 1)
    void calculateTotalMarks_WithinTimeout() throws InterruptedException {
        calculator.clear();
        calculator.appendNumber("50");
        calculator.setOperator('-');
        calculator.appendNumber("25");
        calculator.calculate();
        assertEquals("25.0", calculator.inputField.getText());

    }
}
