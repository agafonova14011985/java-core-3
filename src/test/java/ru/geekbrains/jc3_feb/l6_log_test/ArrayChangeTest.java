package ru.geekbrains.jc3_feb.l6_log_test;


import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.geekbrains.jc3_feb.l6_log_test.home_work.ArrayChangeTwo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class ArrayChangeTest {

    @ParameterizedTest
    @MethodSource("Params")
    void sliceTest(int[] in, int[] out) {
        Assertions.assertArrayEquals(out, ArrayChangeTwo.sliceArray(in));
    }

    static Stream<Arguments> Params() {//параметры
        List<Arguments> list = new ArrayList<>();
        list.add(Arguments.arguments(new int[]{1, 5, 4, 8, 2, 1, 4, 8}, new int[]{8}));
        list.add(Arguments.arguments(new int[]{4}, new int[]{}));
        list.add(Arguments.arguments(new int[]{1, 5, 4, 8, 2}, new int[]{8,2}));
        return list.stream();
    }


    @Test
    void testSlice() {
        Assertions.assertThrows(RuntimeException.class, () -> ArrayChangeTwo.sliceArray(new int[]{1,5,5,5}));

    }

    @Test//Тест на проверку массива на наличие в нем 1 и 4
    void checkArrayTwoTest() {
        Assertions.assertTrue(ArrayChangeTwo.checkArrayTwo(new int[]{1, 1, 1, 4, 4, 4, 1}));
    }

//    @Test()//Тест на появление RuntimeException
//    public void testsLiceArray() {
//        ArrayChangeTwo.sliceArray(new int[]{1,2,3});
//    }

}