package ru.geekbrains.jc3_feb.l1_generics;

public class GenericClass<T> implements GenericInterface<T> {
    @Override
    public T doSomething(T request) {
        return null;
    }
}

//public class GenericClass implements GenericInterface<String> {
//    @Override
//    public String doSomething(String request) {
//        return null;
//    }
//}