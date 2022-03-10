package ru.geekbrains.jc3_feb.l7_reflection.home_work;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Reflection {
    public static void classInfo(Class aClass) {
        System.out.println("Class: " + aClass.getName());
        classParents(aClass);
        classConstructors(aClass);
        classFields(aClass);


        classMethods(aClass);
    }

    private static void classMethods(Class aClass) {
        System.out.println("\nMETHODS");
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
    }

    private static void classFields(Class aClass) {
        System.out.println("\nFIELDS");
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field);
        }
    }

    private static void classConstructors(Class aClass) {
        System.out.println("\nCONSTRUCTORS");
        Constructor[] constructors = aClass.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            System.out.print(constructor);
            System.out.println();
        }
    }

    private static void classParents(Class aClass) {
        Class parent = aClass.getSuperclass();
        if (parent != null) {
            System.out.println("Subclass: " + parent.getSimpleName());
            classParents(parent);
        }
    }
}
