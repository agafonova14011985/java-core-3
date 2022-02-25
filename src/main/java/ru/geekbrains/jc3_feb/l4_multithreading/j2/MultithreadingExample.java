package ru.geekbrains.jc3_feb.l4_multithreading.j2;
//урок с джавы 2 многопоточность
public class MultithreadingExample {

//можем создать разные объекты в виде мониторов
    private static final Object mon1 = new Object();
    private static final Object mon2 = new Object();

    private static int a = 0;
    private static int b = 0;
    private static int c = 0;


    public static void main(String[] args) {
//        threadCreation();
//        threadStopExample();
//        raceConditionExample();


    }



    private static void raceConditionExample() {
        Thread t1 = new Thread(ru.geekbrains.jc3_feb.l4_multithreading.MultithreadingExample::increment);
        Thread t2 = new Thread(ru.geekbrains.jc3_feb.l4_multithreading.MultithreadingExample::increment);
        Thread t3 = new Thread(ru.geekbrains.jc3_feb.l4_multithreading.MultithreadingExample::increment);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("A = %d, B = %d, C = %d\n", a, b, c);
    }

    private static void unSyncMethod() {
        //.... some logic to be sync
    }


    private static void monitorSync() {
        System.out.println("do smth sync....");
        //......

        synchronized (mon1) {
            //.... here is sync code
        }


    }

    private static void monitorSync2() {
        System.out.println("do smth sync2....");
        //......
//синхронизирует только код , тут мы сами выбираем себе монитор /более гибкий/
// но генерится больше кода/медленней

        synchronized (mon2) {
            //.... here is sync code
        }


    }

//если методов синхронизации много, то монитор остается одним и тем же, остальные синхроники ждут


//синхронизирует весь метод, если метод статический то монитором будет весь класс, т е не попадут 2 потока
    private static synchronized void increment() { //mon is class
        for (int i = 0; i < 1000; i++) {
            a++;
            b++;
            c++;
        }
    }
//монитором будет служить сам объект у которого будет вызаваться этот метод, ток как метод не статический,
// два потока не вызовут этот метод одновременно но у разных объектов этого типа могут
    private synchronized void doSync() { //mon is this
        //....
    }



    private static void threadStopExample() {
        Thread main = Thread.currentThread();

        Thread t = new Thread(() -> {
//            while (true) {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000);
                    System.out.println("ffffff - " + main.getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });
//        t.setDaemon(true);
        t.start();

        try {
            Thread.sleep(3000);
            System.out.println("Main finish");
            t.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        t.stop();
//        t.suspend();
//        t.resume();
    }

    private static void threadCreation() {
        MyThread myThread = new MyThread();
//        myThread.run();
        myThread.start();
        MyRunnable myRunnable = new MyRunnable();
        Thread myRunnableThread = new Thread(myRunnable);
        myRunnableThread.start();
//сщздание потоков с помощью анонимки
        Thread anonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.printf("Hello from anonymous. Thread is %s\n", Thread.currentThread().getName());
            }
        });
        anonThread.start();//создание потоков с помощью лямбд
        Thread lambdaThread = new Thread(() -> System.out.printf("Hello from lambda. Thread is %s\n", Thread.currentThread().getName()));
        lambdaThread.start();
        new Thread(() -> System.out.printf("Hello from lambda one line. Thread is %s\n", Thread.currentThread().getName())).start();
        new Thread(() -> System.out.printf("Hello from lambda one line 2. Thread is %s\n", Thread.currentThread().getName())).start();


        System.out.printf("Hello from main. Thread is %s\n", Thread.currentThread().getName());

//создание потоков с помощью ссылок на методы
        new Thread(() -> printSome()).start();
        new Thread(ru.geekbrains.jc3_feb.l4_multithreading.MultithreadingExample::printSome).start();
        new Thread(ru.geekbrains.jc3_feb.l4_multithreading.MultithreadingExample::printSome).start();
        new Thread(ru.geekbrains.jc3_feb.l4_multithreading.MultithreadingExample::printSome).start();
        new Thread(ru.geekbrains.jc3_feb.l4_multithreading.MultithreadingExample::printSome).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        myRunnableThread.start();
    }

    private static void printSome() {
        System.out.println("Print some " + Thread.currentThread().getName());
    }
//создание потоков имплементирующих ранабел
    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.printf("Hello from my runnable. Thread is %s\n", Thread.currentThread().getName());
        }
    }
//создание потоков с помощью классов наследующих трэд
    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.printf("Hello from my thread. Thread is %s\n", Thread.currentThread().getName());
        }


    }
}