package ru.geekbrains.jc3_feb.l1_generics;

//T(ype) E(lement) K(ey) V(alue) - типы данных

//обобщенный тип можно ставить любую букву, слова...
//public class GenericBox<X, Y, Z> {

//тип данных Х
public class GenericBox<X> {
    //ограниечение: нельзя статическое поле объявить
    //    private static X staticField;
    private X obj;

    public GenericBox(X obj) {
        this.obj = obj;
    }

    public GenericBox() {
//        obj = new X();//нельзя создать обект обобщенного типа Х
//        X[] arr = new X[10];//можем объявлять массив, но создать  массив оюобщенного его нельзя
    }

    public X getObj() {
        return obj;//возвращение данных
    }

    public void setObj(X obj) {
        this.obj = obj;
    }

}