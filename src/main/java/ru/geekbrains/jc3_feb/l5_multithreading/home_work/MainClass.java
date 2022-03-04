package ru.geekbrains.jc3_feb.l5_multithreading.home_work;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MainClass {

    static final int CARS_COUNT = 4;
    static final CountDownLatch cdlFinish = new CountDownLatch(CARS_COUNT);
    static final CountDownLatch cdlReady = new CountDownLatch(CARS_COUNT);

    static final CyclicBarrier startBarrier = new CyclicBarrier(CARS_COUNT);




    public static void main(String[] args) throws InterruptedException {



        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!\n");
        Race race = new Race(new Road(60), new Tunnel(80), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            final int randomSpeed = 20 + (int) (Math.random() * 10);
            cars[i] = new Car(race, randomSpeed);
        }
        for (Car car : cars) {
            new Thread(car).start();
        }
        cdlReady.await();
        System.out.println("\nВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!\n");

        cdlFinish.await();
        System.out.println("\nВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }


}

