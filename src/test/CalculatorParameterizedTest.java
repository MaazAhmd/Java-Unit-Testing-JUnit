package test;

import main.Calculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

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

    // Gives 'null' as the input
    @ParameterizedTest
    @NullAndEmptySource
    void testWithNullAndEmptySources(String input) {
        System.out.println("NullAndEmptySource test with input: " + input);
        calculator.clear();
        if (input != null) {
            calculator.appendNumber(input);
        }
        assertTrue(calculator.inputField.getText().isEmpty(), "Field should be empty for null/empty input");
    }

//    @ParameterizedTest
//    @EnumSource(value = Operator.class, names = {"ADD", "SUBTRACT"})
//    void testWithEnumSource(Operator operator) {
//        System.out.println("EnumSource test with operator: " + operator);
//        calculator.clear();
//        calculator.setOperator(operator.getSymbol());
//        assertEquals(operator.getSymbol(), calculator.currentOperator, "Operator mismatch");
//    }

    @ParameterizedTest
    @MethodSource("operations")
    void testWithMethodSource(String n1, char operator, String n2, String result) {
        calculator.clear();
        calculator.appendNumber(n1);
        calculator.setOperator(operator);
        calculator.appendNumber(n2);
        calculator.calculate();
        assertEquals(result, calculator.inputField.getText(), "Operation failed");
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
    @CsvSource({
            "1, 1.0",
            "2, 4.0",
            "3, 9.0",
            "4, 16.0",
            "5, 25.0"
    })
    void testWithCsvSource(int value, String expected) {
        calculator.clear();
        calculator.appendNumber(String.valueOf(value));
        calculator.square();
        assertEquals(expected, calculator.inputField.getText(), "Square calculation failed");
    }

    @ParameterizedTest
    @Disabled("No file present right now.")
    @CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1)
    void testWithCsvFileSource(int value, String expected) {
        calculator.clear();
        calculator.appendNumber(String.valueOf(value));
        calculator.square();
        assertEquals(expected, calculator.inputField.getText(), "Square calculation failed");
    }

}
