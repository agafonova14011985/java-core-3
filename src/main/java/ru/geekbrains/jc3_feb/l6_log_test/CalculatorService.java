package ru.geekbrains.jc3_feb.l6_log_test;

public class CalculatorService {
    private Calculator calculator;

    public CalculatorService(Calculator calculator) {
        this.calculator = calculator;
    }

    public int add(int a, int b) {
        return calculator.add(a, b);
    }

    public int subtract(int a, int b) {
        return calculator.sub(a, b);
    }

    public int multiply(int a, int b) {
        return calculator.mul(a, b);
    }

    public int divide(int a, int b) {
        return calculator.div(a, b);
    }
}