package ru.geekbrains.jc3_feb.l1_generics.home_work.two;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FruitBox<F extends Fruit> {
    private List<F> fruits;

    public FruitBox() {
        this.fruits = new ArrayList<>();
    }

    public FruitBox(List<F> fruits) {
//        this.fruits = fruits; //Не лучший вариант инициализации из-за непредсказуемых вариантов реализации
        this.fruits = new ArrayList<>(fruits);
    }

    public FruitBox(F... fruits) {
        this.fruits = new ArrayList<>(Arrays.asList(fruits));
    }

    public void add(F fruit) {
        this.fruits.add(fruit);
    }

    public double getWeight() {
//        if (this.fruits.size() == 0) return 0.0;
//        return this.fruits.size() * this.fruits.get(0).getWeight();
        double weight = 0.0;
        for (F fruit : fruits) {
            weight += fruit.getWeight();
        }
        return weight;
    }

    public boolean equalsByWeight(FruitBox<?> another) {
        return Math.abs(getWeight() - another.getWeight()) < 0.0001;
    }

    public void transferAll(FruitBox<? super F> another) {
        if (this == another) return;
        another.fruits.addAll(fruits);
        fruits.clear();
    }

    public List<F> getFruits() {
        return fruits;
    }
}