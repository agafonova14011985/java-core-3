package ru.geekbrains.jc3_feb.l1_generics.home_work.two;

import java.util.ArrayList;

public class ArrayUtil {

    public static void replaceTwoElements(Object[] arr, int i1, int i2) {
        Object temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }

    public static <T> ArrayList<T> arrToList(T[] arr) {
//        return new ArrayList<T>(Arrays.asList(arr));
        ArrayList<T> result = new ArrayList<T>(arr.length);
        for (T t : arr) {
            result.add(t);
        }
        return result;
    }
}