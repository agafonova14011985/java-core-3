package ru.geekbrains.jc3_feb.l6_log_test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
//генерим код из калькулятора тест выбираем методы для тестов галочками и ок
//добавляем зависимости в пом (см класс калькулятьр)
class CalculatorTest {

    private Calculator calculator; //обязательно объявление

    //тестируем что калькулятор работает нормально

    @BeforeEach//запускается перед каждым тестом
    void init() {//метод инициализации калькулятора/ обязательно!!
        calculator = new Calculator();
    }

    @AfterEach//происходит после каждого теста
    void dispose() {
        System.out.println("Dispose");
    }

    @BeforeAll//запускают метод однажды и запуск перед
    static void initAll() {
        System.out.println("Init All");
    }

    @AfterAll//запуск в конце
    static void disposeAll() {
        System.out.println("Dispose All");
    }

    @Test
    void add() {
        int a = 10;//
        int b = 10;
        int result = 20;
        //проверка правильно ли выполняется сложение
        //в Assertions можно выбрать много методов/для калькулятора хорошо assertEquals
        Assertions.assertEquals(result, calculator.add(a, b));
    }

    //    @CsvSource({
//            "1,2,3",
//            "4,5,9",
//            "9,1,10"
//    })
//    @CsvFileSource(files = {"test_files/t1.csv", "test_files/t2.csv"})
    @MethodSource("generateData")
    @ParameterizedTest
    void massAddTest(int a, int b, int res) {
        Assertions.assertEquals(res, calculator.add(a, b));
    }


    private static Stream<Arguments> generateData() {
        int limit = 100_000;
        List<Arguments> args = new LinkedList<>();
        for (int i = 0; i < limit; i++) {
            int a = (int) (Math.random() * limit);
            int b = (int) (Math.random() * limit);
            int res = a + b;
            args.add(Arguments.arguments(a, b, res));
        }
        return args.stream();
    }

    @Test
    @Disabled
    void sub() {
        int a = 10;
        int b = 10;
        int result = 0;
        Assertions.assertEquals(result, calculator.sub(a, b));//можно так
        Assertions.assertEquals(14, calculator.sub(21, 7));//или так
        Assertions.assertEquals(2, calculator.sub(4, 2));
        Assertions.assertEquals(3, calculator.sub(5, 2));
        Assertions.assertEquals(1, calculator.sub(3, 2));
    }

    @Test
    void mul() {
        Assertions.fail();
    }

    @Test//проверка что случится исключение если выполнится след код
    @Timeout(value = 5, unit = TimeUnit.MINUTES)
    void div() {
        Assertions.assertThrows(ArithmeticException.class, () -> calculator.div(10, 0));
    }
}