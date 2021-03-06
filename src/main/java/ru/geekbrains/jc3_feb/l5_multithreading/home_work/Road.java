package ru.geekbrains.jc3_feb.l5_multithreading.home_work;

public class Road extends Stage {

    Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";

    }
    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}