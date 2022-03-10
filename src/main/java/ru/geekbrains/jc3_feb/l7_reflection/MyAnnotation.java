package ru.geekbrains.jc3_feb.l7_reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//анотация опред область видимости анатации
@Target({ElementType.TYPE, ElementType.FIELD})//назначение нашей анотации
public @interface MyAnnotation {
    //свойства для анатации//нужна инициализация
    String data() default "";//или значнение по умолчанию
}