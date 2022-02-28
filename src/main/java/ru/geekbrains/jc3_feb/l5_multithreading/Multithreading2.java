package ru.geekbrains.jc3_feb.l5_multithreading;

import java.util.*;
import java.util.concurrent.*;//многопоточка
import java.util.concurrent.locks.Lock;//замки
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Multithreading2 {
    public static void main(String[] args) throws InterruptedException {
//        simpleReentrantLockExample();
//        reentrantReadWriteLockExample();
//        simpleCountDownLatchExample();
//        anotherCountDownLatchExample();
//        semaphoreExample();
//        simpleCyclicBarrierExample();
//        barrierWithRunnable();
//        concurrentCollectionsExample();

    }

    //коллекции
    private static void concurrentCollectionsExample() {
        //список
        List<String> list = new ArrayList<>();//на массивах//для чтения если многопоточка
        List<String> syncListOld = new Vector<>();//вектор - такой же как арей лист, почти /медленней/не хранит налы/
        //надежный но медленный
        List<String> syncList = new CopyOnWriteArrayList<>();//оптимизированны под многопоток/еае арейлист/но методы не засинхронизированны/читают его все, но при адд - особенности- старый массив подменяется новым(копией)
        //получае в качестве аргумента список не сихрониз, заварачивает в синхронизацию
        List<String> syncListFast = Collections.synchronizedList(list);

        //
        Set<String> set = new HashSet<>();
        //синхронизированный сет/обертка CopyOnWriteArrayList
        Set<String> syncSet = new CopyOnWriteArraySet<>();//параллельно много читают/и иногда меняют
        //завернут сет в синхро оболочку
        Set<String> syncSetFast = Collections.synchronizedSet(set);


        //
        Map<String, String> map = new HashMap<>();
        Map<String, String> syncMapOld = new Hashtable<>();//все методы синхронизированны/медленно/не удобно
        //ConcurrentHashMap - крутая / немного медленней/в ней находится массив тэйюл
        //имеются ноды, есть и явная и неявная синхронизация/ синхронизация идет поячеечно/чтение различно/ если изменение данных, то блокировка блакируется только конкретной ячейки массива
        Map<String, String> syncMap = new ConcurrentHashMap<>();
        //заворачиваем мапу в синхронайз
        Map<String, String> syncMapFast = Collections.synchronizedMap(map);
    }

    //перегруженный конструктор, ожидает количество и ранабл - срабатывает когда борьер всех отпускает
    private static void barrierWithRunnable() {
        var carsCount = 10;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(carsCount, () -> {
            System.out.println("CHECKPOINT");
        });
        for (int i = 0; i < carsCount; i++) {
            var j = i + 1;

            new Thread(() -> {
                try {
                    System.out.printf("Car #%d preparing\n", j);
                    Thread.sleep((long) (350 + 850 * Math.random()));
                    System.out.printf("Car #%d on the start line\n", j);
                    cyclicBarrier.await();//ждет пока все не займут
                    System.out.printf("Car #%d riding\n", j);
                    Thread.sleep((long) (350 + 850 * Math.random()));
                    System.out.printf("Car #%d finished\n", j);
                    cyclicBarrier.await();
                    System.out.printf("Car #%d rides to garage\n", j);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
            //cyclicBarrier.reset();сброс
           // cyclicBarrier.isBroken()//много реализаций
        }
    }

    //может останавливать потоки на опред этапе + циклический/многоразовый
    private static void simpleCyclicBarrierExample() {
        var carsCount = 10;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(carsCount);//объявление с колво тормозях потоков
        for (int i = 0; i < carsCount; i++) {
            var j = i + 1;

            new Thread(() -> {
                try {
                    //приготовление
                    System.out.printf("Машина #%d подготовка\n", j);
                    Thread.sleep((long) (350 + 850 * Math.random()));
                    System.out.printf("Машина #%d на линии старта\n", j);
                    cyclicBarrier.await();//10 раз отщелкнет//не может стартовать без сигнала/ждать пока не уменьшиться до  0
                    System.out.printf("Машина #%d едет\n", j);
                    Thread.sleep((long) (350 + 850 * Math.random()));
                    System.out.printf("Машина #%d на финише\n", j);
                    cyclicBarrier.await();//ждет всех и едет в гараж
                    System.out.printf("Машина #%d едет в гараж\n", j);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    //мультимьютекс
   //симафор - если есть критические области какойто опред. максимум потоков
   //это ограничение ширина шоссе - 20 потоков, но на мосту может проехать только опреед кол во
   //ограничивает макс кол во потоков
    private static void semaphoreExample() {
        //        Semaphore semaphore = new Semaphore(3);//объявление интовое число пермит, сколькими можно занять
        Semaphore semaphore = new Semaphore(3, true);//перегруженный конструктор, если мост занят то остальные стоят в очередь, гарантия порядка первоочередности
        for (int i = 0; i < 20; i++) {//не больше 20 потоков
            var j = i + 1;//для лямбды
            new Thread(() -> {
                try {
                    //машина 1 перед мостом
                    System.out.printf("Car #%d before the bridge\n", j);
                    //что бы заехать на мост вызываем
                    semaphore.acquire();
                    System.out.printf("Car #%d riding for a long time on the bridge\n", j);
                   //имитяция езды по мосту
                    Thread.sleep((long) (350 + 850 * Math.random()));
                    //закончила ехат по мосту
                    System.out.printf("Car #%d finished the bridge\n", j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //когда мост освобожден другая машина может заехать
                } finally {
                    semaphore.release();//многоразовый
                }
            }).start();
        }
    }

    //CountDownLatch - щелчки
    private static void anotherCountDownLatchExample() throws InterruptedException {
        var threadCount = 10;
        CountDownLatch cdl = new CountDownLatch(threadCount + 1);//не могут ощелкнуть последний
        System.out.println("Begin");

        for (int i = 0; i < threadCount; i++) {
            var j = i;

            new Thread(() -> {
                try {
                    System.out.printf("Thread #%d started\n", j);
                    Thread.sleep((long) (350 + 850 * Math.random()));
                    //синхронизация не только мейна но что бы завершили все потоки работу одновременно
                    cdl.countDown();//щелкнули но ждем остальных
                    cdl.await();
                    System.out.printf("Thread #%d done\n", j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }


        while (cdl.getCount() > 1) {
            Thread.sleep(50);
        }
        System.out.println("Finishing");
        cdl.countDown();//последний щелчок
        Thread.sleep(50);//немного ждем
        System.out.println("ALL JOB DONE");
    }

    //реализует обратный отсчет, содержит счетчик, похоже на джойн
    private static void simpleCountDownLatchExample() {
        var threadCount = 10;//10 потоков
        CountDownLatch cdl = new CountDownLatch(threadCount);//объявляем/ threadCount - число
//        CountDownLatch cdl = new CountDownLatch(threadCount + 1);//ждать вечно
//        CountDownLatch cdl = new CountDownLatch(threadCount / 2);//ждем выполнение только половины потоков
        System.out.println("Begin");

        //одем от 0- кол - во потоков
        for (int i = 0; i < threadCount; i++) {
            var j = i;// что бы видела лямбда

            new Thread(() -> {
                try {
                    System.out.printf("Thread #%d started\n", j);//старт
                    Thread.sleep((long) (350 + 850 * Math.random()));//имитация работы
                    System.out.printf("Thread #%d done\n", j);//закончил работу
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    cdl.countDown();//становится 10 потом 9.....1 и остановка
                }
            }).start();
        }

        try {
            cdl.await();//подождать и печать
            System.out.println("ALL JOB DONE");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //умеет отдельно блокировать операции записи и операции чтение
    private static void reentrantReadWriteLockExample() {
        //Много потоков могут одновременно читать одни данные
        //Писать данные может только один поток в единицу времени
        //Когда один поток пишет - никто не читает, и не пишет

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        new Thread(() -> {
            try {
                lock.readLock().lock();//из замка вызаваем readLock() - чтение  а из него lock() - запираем
                System.out.println("Start READ 1");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Finished READ 1");
                lock.readLock().unlock();//когда заканчиваем то readLock().unlock()
            }
        }).start();//старт

        new Thread(() -> {
            try {
                lock.readLock().lock();
                System.out.println("Start READ 2");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Finished READ 2");
                lock.readLock().unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.readLock().lock();
                System.out.println("Start READ 3");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Finished READ 3");
                lock.readLock().unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.writeLock().lock();
                System.out.println("Start WRITE 1");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Finished WRITE 1");
                lock.writeLock().unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.readLock().lock();
                System.out.println("Start READ 4");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Finished READ 4");
                lock.readLock().unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.writeLock().lock();
                System.out.println("Start WRITE 2");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Finished WRITE 2");
                lock.writeLock().unlock();
            }
        }).start();
    }

    //замок, аналог синхронайза, но в виде отдельного класса
    //замок занимаем и освобождаем явно,
    private static void simpleReentrantLockExample() {
        Lock lock = new ReentrantLock();// ReentrantLock- объявление

        //запуск потока, в нем печать
        new Thread(() -> {
            System.out.println("Before lock 1");
            try {
                //что бы занять область синхронизировонную замком
                lock.lock();//обращаемся к локу и вызавам метод лок
                System.out.println("Got lock 1");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                //замок освобождаем  и вызаваем в finally методы lock.unlock
            } finally {
                System.out.println("Lock 1 released");
                lock.unlock();
            }
        }).start();

//        new Thread(() -> {
//            System.out.println("Before lock 2");
//            try {
//                lock.lock();
//                System.out.println("Got lock 2");
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                System.out.println("Lock 2 released");
//                lock.unlock();
//            }
//        }).start();


        new Thread(() -> {
            System.out.println("Before lock 3");
//            if (lock.tryLock()) {//обычный /еще замок можно занимать /булевое значение
            try {
                if (lock.tryLock(4, TimeUnit.SECONDS)) {//с таймаутом дождется освобождение  1 лока
                    try {
                        System.out.println("Got lock 3");
                        Thread.sleep(3000);
                    } finally {
                        System.out.println("Lock 3 released");
                        lock.unlock();
                    }
                } else {
                    System.out.println("Lock 3 was busy");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}