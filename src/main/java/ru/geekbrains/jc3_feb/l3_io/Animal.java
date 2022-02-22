package ru.geekbrains.jc3_feb.l3_io;

import java.io.Serializable;

public class Animal implements Serializable {
    private String type;

    public Animal(String type) {
        this.type = type;
        System.out.println("Animal born");
    }
}