package test;

import main.Calculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorParameterizedTest {
    static Calculator calculator;

    @BeforeAll
    static void setUp() {
        calculator = new Calculator();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void calculateSquareWithValueSource(int value) {
        System.out.println("ValueSource test with value: " + value);
        calculator.clear();
        calculator.appendNumber(String.valueOf(value));
        calculator.square();
        float expectedResult = value * value;
        assertEquals(String.valueOf(expectedResult), calculator.inputField.getText());
    }

    private static Object[] operations(){
        return new Object[]{
                new Object[]{"20",'+',"30","50.0"},
                new Object[]{"10",'-',"10","0.0"},
                new Object[]{"4",'*',"3","12.0"},
                new Object[]{"8",'/',"2","4.0"},

        };
    }
    @ParameterizedTest
    @MethodSource("operations")
    void testArithmeticOperators(String n1, char operator, String n2, String result) {
        calculator.clear();
        calculator.appendNumber(n1);
        calculator.setOperator(operator);
        calculator.appendNumber(n2);
        calculator.calculate();

        // Basic assertion to check the result
        assertEquals(result, calculator.inputField.getText(), "Operation failed");
    }
}