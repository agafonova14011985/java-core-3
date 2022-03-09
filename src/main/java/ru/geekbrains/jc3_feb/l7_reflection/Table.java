package ru.geekbrains.jc3_feb.l7_reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
//таблица для класса
@Target({ElementType.TYPE})
public @interface Table {
    //задовать имя табл
    String name();
}