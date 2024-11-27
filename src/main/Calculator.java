package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {

    public JTextField inputField;
    private double num1, num2, result;
    private char operator;

    public Calculator() {
        initializeUI();
    }

    private void initializeUI() {
        // Set the frame title
        setTitle("Scientific Calculator");

        // Create input field
        inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        inputField.setEditable(false);
        inputField.setFont(new Font("Arial", Font.BOLD, 24)); // Larger font
        inputField.setPreferredSize(new Dimension(0, 50)); // Increase height

        // Create buttons
        String[] buttonLabels = {
                "7", "8", "9", "/", "sqrt",
                "4", "5", "6", "*", "x^2",
                "1", "2", "3", "-", "x^3",
                "C", "0", "=", "+", "x^y",
                "sin", "cos", "tan", "log", "ln"
        };

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 5, 5, 5)); // 5x5 grid with spacing

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this);
            button.setBackground(Color.BLACK); // Button background color
            button.setForeground(Color.WHITE); // Button text color
            button.setFont(new Font("Arial", Font.BOLD, 16)); // Button font
            buttonPanel.add(button);
        }

        // Layout configuration
        setLayout(new BorderLayout(10, 10)); // Add spacing around components
        add(inputField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Frame settings
        setSize(500, 600); // Increased width for broader input field
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            if (command.equals("C")) {
                clear();
            } else if (command.equals("=")) {
                calculate();
            } else if (command.equals("sqrt")) {
                squareRoot();
            } else if (command.equals("x^2")) {
                square();
            } else if (command.equals("x^3")) {
                cube();
            } else if (command.equals("sin")) {

                sine();
            } else if (command.equals("cos")) {
                cosine();
            } else if (command.equals("tan")) {
                tangent();
            } else if (command.equals("log")) {
                logarithm();
            } else if (command.equals("ln")) {
                naturalLogarithm();
            } else if (command.equals("x^y")) {
                power();
            } else if ("+-*/".contains(command)) {
                setOperator(command.charAt(0));
            } else {
                appendNumber(command);
            }
        } catch (NumberFormatException ex) {
            inputField.setText("Error");
        }
    }

    // Function to clear input
    public void clear() {
        inputField.setText("");
        num1 = num2 = result = 0;
        operator = '\0';
    }

    // Function to calculate result based on the operator
    public void calculate() {
        num2 = Double.parseDouble(inputField.getText());
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    throw new ArithmeticException("Division by zero");
                }
                break;
            case '^':
                result = Math.pow(num1, num2);
                break;
            default:
                return;
        }
        inputField.setText(String.valueOf(result));
    }

    // Function for square root operation
    public void squareRoot() {

        if (num1 > 0) {
            num1 = Double.parseDouble(inputField.getText());
            result = Math.sqrt(num1);
            inputField.setText(String.valueOf(result));
        }

        throw new IllegalArgumentException("Cannot compute square root of a negative number");
    }

    // Function for square operation
    public void square() {
        num1 = Double.parseDouble(inputField.getText());
        result = Math.pow(num1, 2);
        inputField.setText(String.valueOf(result));
    }

    // Function for cube operation
    public void cube() {
        num1 = Double.parseDouble(inputField.getText());
        result = Math.pow(num1, 3);
        inputField.setText(String.valueOf(result));
    }

    // Function for sine operation
    public void sine() {
        num1 = Double.parseDouble(inputField.getText());
        result = Math.sin(Math.toRadians(num1));
        inputField.setText(String.valueOf(result));
    }

    // Function for cosine operation
    public void cosine() {
        num1 = Double.parseDouble(inputField.getText());
        result = Math.cos(Math.toRadians(num1));
        inputField.setText(String.valueOf(result));
    }

    // Function for tangent operation
    public void tangent() {
        num1 = Double.parseDouble(inputField.getText());
        result = Math.tan(Math.toRadians(num1));
        inputField.setText(String.valueOf(result));
    }

    // Function for logarithm (base 10) operation
    public void logarithm() {
        num1 = Double.parseDouble(inputField.getText());
        if (num1 <= 0) {
            throw new IllegalArgumentException("Logarithm undefined for zero or negative numbers");
        }
        result = Math.log10(num1);
        inputField.setText(String.valueOf(result));
    }

    // Function for natural logarithm operation
    public void naturalLogarithm() {
        num1 = Double.parseDouble(inputField.getText());
        if (num1 <= 0) {
            throw new IllegalArgumentException("Natural logarithm undefined for zero or negative numbers");
        }
        result = Math.log(num1);
        inputField.setText(String.valueOf(result));
    }

    // Function for power (x^y) operation
    private void power() {
        num1 = Double.parseDouble(inputField.getText());
        inputField.setText("");
        operator = '^';
    }

    // Function to set the operator
    public void setOperator(char op) {
        num1 = Double.parseDouble(inputField.getText());
        operator = op;
        inputField.setText("");
    }

    // Function to append number or operator to input field
    public void appendNumber(String number) {
        inputField.setText(inputField.getText() + number);
    }
}
