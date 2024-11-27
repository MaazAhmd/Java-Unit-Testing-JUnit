package test;

import main.SimpleCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;
import static org.junit.jupiter.api.Assertions.*;

class ConditionalTesting {

    SimpleCalculator calculator = new SimpleCalculator();

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testAdditionOnWindows() {
        assertEquals(5, calculator.add(2, 3), "Addition failed on Windows");
    }

    @Test
    @DisabledOnOs({OS.LINUX,OS.WINDOWS})
    void testSubtractionNotOnLinux() {
        assertEquals(1, calculator.subtract(3, 2), "Subtraction ran on Linux but should be disabled");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void testMultiplicationOnJava8() {
        assertEquals(6, calculator.multiply(2, 3), "Multiplication failed on Java 8");
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_11, max = JRE.JAVA_17)
    void testDivisionOnJava11To17() {
        assertEquals(2, calculator.divide(10, 5), "Division failed on Java 11 to 17");
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "true")
    void testAdditionNotOnCI() {
        assertEquals(9, calculator.add(4, 5), "Addition ran on CI but should be disabled");
    }

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".64.")
    void testMultiplicationOn64Bit() {
        assertEquals(15, calculator.multiply(5, 3), "Multiplication failed on 64-bit architecture");
    }

    @Test
    @DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "Graphics environment is headless")
    void testGraphicsDependent() {
        assertEquals(100, calculator.multiply(10, 10), "Test ran in a headless environment but should be disabled");
    }

    @Test
    @EnabledIf("customCondition")
    void customConditionTest() {
        assertEquals(25, calculator.multiply(5, 5), "Test failed for custom condition");
    }

    static boolean customCondition() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
}