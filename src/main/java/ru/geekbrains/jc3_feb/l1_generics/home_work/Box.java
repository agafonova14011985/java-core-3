package ru.geekbrains.jc3_feb.l1_generics.home_work;

import java.util.ArrayList;
import java.util.Arrays;

public class Box<T extends Fruit> {
    private final ArrayList<T> items;

    public Box(T... items) {
        this.items = new ArrayList<T>(Arrays.asList(items));
    }



    public ArrayList<T> getItems() {
        return new ArrayList<T>(items);
    }

    public void clear() {
        items.clear();
    }

    public float getWeight() {
        if (items.size() == 0) {
            return 0;}
        float weight = 0;
        for (T item: items) weight += item.getWeight();

        return weight;
    }

    public boolean compare(Box box) {
        return this.getWeight() == box.getWeight();
    }


}