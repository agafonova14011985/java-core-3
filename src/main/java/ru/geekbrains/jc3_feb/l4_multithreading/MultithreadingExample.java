package ru.geekbrains.jc3_feb.l4_multithreading;

import java.util.concurrent.*;

public class MultithreadingExample {



    private static final Object mon = new Object();//монитор

    private static int order = 0;//объявление


    public static void main(String[] args) {
//        callableExample();
//        executorServiceExample();
//        scheduledExecutorExample();

        new Thread(() -> {//создание потока
            for (int i = 0; i < 3 ; i++) {
                synchronized (mon) {//синхронизация
                    try {
                        while (order != 0) {//если ордер не = 0
                            mon.wait();//поток ожидает/спит, но при этом освобождает монитор, разбудить можно по таймауту, либо методом notifyAll();
                        }
                        printHello();//печать
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
                        printWorld();
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
            for (int i = 0; i < 7; i++) {
                synchronized (mon) {
                    try {
                        while (order != 2) {
                            mon.wait();
                        }
                        printSign();
                        Thread.sleep(150);
                        order = 0;
                        mon.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private static void printHello() {
        System.out.print("Hello");
    }

    private static void printWorld() {
        System.out.print(" world");
    }

    private static void printSign() {
        System.out.println("!");
    }
//наследует executorService, теже тред пулы но еще выполняет методы....отложенное время, +...промежуток между выполнением
    private static void scheduledExecutorExample() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(() -> System.out.println("mkfkm"), 1, 1, TimeUnit.SECONDS);
    }
//THREAD_POOL - оптимизированный
    //поток нельзы запустить дважды
    //верхнеуровневая оболочка для потоков, не тратятся ресурсы
    private static void executorServiceExample() {
        //ExecutorService - интерфейс описывает что могут делать/ фабрика - Executors
        //ewSingleThreadExecutor - создает отдельный 1 поток и работает только с ним, но может выполнять много задач
        //просто так не останавливает свою работу
        //       ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
//            @Override//возвращает тред, получает ранабл/метод настройки потоков, будут прохоть через него все задачи
        //в виде ранаблла
//            public Thread newThread(Runnable r) {
        //создаем
//                var t = new Thread(r);
//                t.setName("THREAD_POOL");//любое действие, что потоки демоны, приоритеты,  или назначить имя потока
//                return t;//возвращаем
//            }
//        });
//        ExecutorService executorService = Executors.newSingleThreadExecutor();

  //     ExecutorService executorService = Executors.newFixedThreadPool(4);//позволяет создать опред кол во потоков и работать только с ними

            ExecutorService executorService = Executors.newCachedThreadPool();//пул поток сколько угодно потоков под задачи
        // нужно быть осторожней с памятью/есть время жизни потока

        for (int i = 0; i < 10; i++) {
            var j = i;
            //для того что бы  executorService выполнил задачу нужно execute или субмит/
            //передается ранабл в виде (() -> { лямбды
            //лямбда видит содержимое метода и полей класса, но не работают с изменяемыми значениями
            executorService.execute(() -> {
                //получили имя потока
                System.out.printf("Task #%s started. Thread name: %s\n", j, Thread.currentThread().getName());
                try {
                    //пусть время выполнения 1500.....
                    Thread.sleep((long) (1500 + 1500 * Math.random()));//имитация работы
                } catch (InterruptedException e) {//изи за слипп
                    e.printStackTrace();
                }
                System.out.printf("Task #%s finished\n", j);
            });
        }

        var future = executorService.submit(() -> "Hello world");

        System.out.println("All tasks are given");

        executorService.shutdown();//надо бы завершать работу, executorService не прекращает работу а доделывает не принимая новых задач
//        executorService.shutdownNow();//возвращает список задач которые были в очереди и не были выполненны + исключение+ заставляет вызвать интерапт на всех потоков
        try {
            executorService.awaitTermination(20, TimeUnit.SECONDS);//awaitTermination - распечатает инф после 20 сек, генерирует интераптет и таймаут исключение
            System.out.println(future.get());
            System.out.println("Finish");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void callableExample() {
        //FutureTask - интерфейс обобщенный указаваем тип данных, который мы хотим достать из нашего потока
        //в качестве аргумента ожидает  Callable<String> того же типа,  Callable - возвращает значение
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override //call() - возвращает исключение или строке
            public String call() throws Exception {
                Thread.sleep(3000);
               throw new RuntimeException("HAHAHA");
            //    return "Hello world";
            }
        });
//запускаем отдельный поток, для этого нужен экземпляр класса тред
        new Thread(futureTask).start();//запуск

        try {
//            var result = futureTask.get();//получаем значение методом -get()- оставляет ожидать сколько угодно
            var result = futureTask.get(10, TimeUnit.MINUTES);//ждем 10 минут или не ждем вообще
            System.out.println(result);
        } catch (InterruptedException e) {//
            e.printStackTrace();
        } catch (ExecutionException e) {//иключение в методе call
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void printSome() {
    }

    public static void increment() {
    }
}