package ru.geekbrains.jc3_feb.l1_generics;

public class Box {
    private Object obj;//Объект хранящий в себе объекты. Ссылка типа Object любой объект


    //конструктор
    public Box(Object obj) {
        this.obj = obj;
    }

    //геттор
    public Object getObj() {
        return obj;
    }

    //сеттор
    public void setObj(Object obj) {
        this.obj = obj;
    }

}