package ru.geekbrains.jc3_feb.l4_multithreading.home_work;

/*      Практическая 4
     1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС).
        Используйте wait/notify/notifyAll.
 */

public class MultithreadingABC {

    private static final Object mon = new Object();//монитор

    private static int order = 0;//объявление

    public static void main(String[] args) {

        new Thread(() -> {//создание потока
            for (int i = 0; i < 5 ; i++) {
                synchronized (mon) {//синхронизация
                    try {
                        while (order != 0) {//если ордер не = 0
                            mon.wait();//поток ожидает/спит, но при этом освобождает монитор,
                                       // разбудить можно по таймауту, либо методом notifyAll();
                        }
                        System.out.printf("A");;//печать
                        Thread.sleep(150);//ждем и меняем ордер на1
                        order = 1;
                        mon.notifyAll();//будим остальные потоки
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5 ; i++) {
                synchronized (mon) {
                    try {
                        while (order != 1) {
                            mon.wait();
                        }
                        System.out.printf("B");;
                        Thread.sleep(150);//ждем и меняем на2
                        order = 2;
                        mon.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                synchronized (mon) {
                    try {
                        while (order != 2) {
                            mon.wait();
                        }
                        System.out.printf("C");;
                        Thread.sleep(150);
                        order = 0;
                        mon.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("\nРабота трех синхронизированных потоков + метод wait() и notifyAll().");
        }).start();

    }

    }


