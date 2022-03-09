package ru.geekbrains.jc3_feb.l7_reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})//вешаем над полем
public @interface Column {
    //анатация для колонок табл
    String name() default "";
}