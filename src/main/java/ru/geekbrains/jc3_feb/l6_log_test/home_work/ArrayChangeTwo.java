package ru.geekbrains.jc3_feb.l6_log_test.home_work;

/*
2. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку,
иначе в методе необходимо выбросить RuntimeException.
Написать набор тестов для этого метода (по 3-4 варианта входных данных).
Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
3. Написать метод, который проверяет состав массива из чисел 1 и 4.
Если в нем нет хоть одной четверки или единицы, то метод вернет false;
Написать набор тестов для этого метода (по 3-4 варианта входных данных).
 */

import java.util.Arrays;


public class ArrayChangeTwo {
    public static void main(String[] args) {

        int[] arr = {1, 1, 4, 4, 4, 4, 1, 1, 4, 1, 4, 4, 1};
        int[] arr1 = {1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1};


        System.out.println("Первый массив " + Arrays.toString(arr));
        System.out.println("Проверка первого массива на наличие в нем 1 и 4 -\n " + checkArrayTwo(arr));

        System.out.println("Второй массив " + Arrays.toString(arr1));
        System.out.println("Проверка второго массива на наличие в нем 1 и 4 -\n " + checkArrayTwo(arr1));


        System.out.println("Исходный массив : " + Arrays.toString(arr));
        System.out.println("Обработанный массив : " + Arrays.toString(sliceArray(arr)));

        System.out.println("Исходный массив : " + Arrays.toString(arr1));
        System.out.println("Обработанный массив : " + Arrays.toString(sliceArray(arr1)));

    }

    public static boolean checkArrayTwo(int[] mas) {
        boolean one = false;
        boolean four = false;

        for (int i = 0; i < mas.length; i++) {
            if (one & four) break;
            if (mas[i] == 1) one = true;
            if (mas[i] == 4) four = true;
        }

        return (one && four) ? (true) : (false);
    }

    public static int[] sliceArray(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] == 4) {
                return Arrays.copyOfRange(arr, i + 1, arr.length);
            }
        }
        throw new RuntimeException("В массиве нет 4");
    }

}