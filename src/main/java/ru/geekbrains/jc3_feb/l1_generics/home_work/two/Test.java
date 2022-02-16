package ru.geekbrains.jc3_feb.l1_generics.home_work.two;

public class Test {
    public static void main(String[] args) {


        FruitBox<Apple> appleFruitBox = new FruitBox<>(new Apple(), new Apple(), new Apple());
//        FruitBox f = new FruitBox();
        FruitBox<Orange> oranges = new FruitBox<>(new Orange(), new Orange());

        System.out.println(appleFruitBox.equalsByWeight(oranges));

        FruitBox<Orange> orangeFruitBox = new FruitBox<>(new Orange());

        System.out.println(oranges.getWeight());
        oranges.transferAll(orangeFruitBox);
        System.out.println(orangeFruitBox.getWeight());
        System.out.println(oranges.getWeight());
//        oranges.add(new Apple());
//        appleFruitBox.add(new Orange());
//        System.out.println(appleFruitBox.getClass().getName());
//        System.out.println(f.getClass().getName());
//        System.out.println(oranges.getClass().getName());

//        appleFruitBox.add(new Orange());

    }
}