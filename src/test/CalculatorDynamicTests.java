package test;

import main.Calculator;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorDynamicTests {

    private final Calculator calculator = new Calculator();

    @TestFactory
    List<DynamicTest> dynamicTestsForCalculateTotalMarks() {
        List<DynamicTest> tests = new ArrayList<>();

        tests.add(DynamicTest.dynamicTest("Test Addition",
                () -> {
                    calculator.clear();
                    calculator.appendNumber("5");
                    calculator.setOperator('+');
                    calculator.appendNumber("10");
                    calculator.calculate();

                    // Basic assertion to check the result
                    assertEquals("15.0", calculator.inputField.getText(), "Addition failed");

                }));

        tests.add(DynamicTest.dynamicTest("Test Subtraction",
                () -> {
                    calculator.clear();
                    calculator.appendNumber("5");
                    calculator.setOperator('-');
                    calculator.appendNumber("10");
                    calculator.calculate();

                    // Basic assertion to check the result
                    assertEquals("-5.0", calculator.inputField.getText(), "Subtraction failed");

                }));


        return tests;
    }
}
