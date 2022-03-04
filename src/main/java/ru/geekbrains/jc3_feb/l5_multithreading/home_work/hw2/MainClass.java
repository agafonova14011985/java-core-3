package ru.geekbrains.jc3_feb.l5_multithreading.home_work.hw2;

public class MainClass {

    public static void main(String[] args) {
        Race race = new Race(8,
                new Road(60),
                new Tunnel(80, 4),
                new Road(40),
                new Tunnel(30, 2),
                new Road(100),
                new Road(20));

        race.makeRace();
    }
}