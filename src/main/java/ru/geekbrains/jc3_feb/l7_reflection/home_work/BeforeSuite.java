package ru.geekbrains.jc3_feb.l7_reflection.home_work;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BeforeSuite {
}