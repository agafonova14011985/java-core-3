package ru.geekbrains.jc3_feb.l5_multithreading.home_work;


import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    //ограничивает макс кол во потоков
    static Semaphore semaphore;

    Tunnel(int length) {
        this.length = length;
        this.description = "Тоннель " + this.length + " метров";
    }
    static {
        semaphore = new Semaphore(MainClass.CARS_COUNT / 2);//
    }
    @Override//
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
            semaphore.acquire(); //что бы заехать  вызываем acquire()
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //когда освобождается, то может ехать другая машина
            semaphore.release();

            System.out.println(c.getName() + " закончил этап: " + description);
        }
    }
}