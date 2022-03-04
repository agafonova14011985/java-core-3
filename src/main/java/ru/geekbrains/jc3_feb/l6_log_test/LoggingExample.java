package ru.geekbrains.jc3_feb.l6_log_test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//используем библиотеку апачи             <groupId>org.apache.logging.log4j</groupId>

//шесть уровней логирования//уровни сообщений самое важное сообщение это Fatal
// Trace << Debug << Info << Warn << Error << Fatal

public class LoggingExample {

    //не перегруженный конструктор
    //    private static final Logger log = LogManager.getLogger();
//    private static final Logger log = LogManager.getLogger("root");//вызов логера по имени

    //создаем объект логера, получаем его из фабрики логменеджер/пользуемся методом getLogger(LoggingExample.class)
   //логеры обычно делают для класса
    private static final Logger log = LogManager.getLogger(LoggingExample.class);//берем логер для данного класса

    public static void main(String[] args) {

        double d = .000324;//добвление в сообщение логов
        String s = "Hi";

        //различные методы//логируют переданную информацию на разных уровнях
        //если код хороший то можно не использовать log.trace
        //если новое приложение то выводим  log.info и log.trace
        //логирование произврдится везде гду есть код и потоки
        log.info("Number = {} and string = {}", d, s);//double d и String s
        log.trace("Trace log");//записывают все все мелочи, записывается вся инфа после срабатывания каждой строчки кода
        log.debug("Debug log");//инфа для дебага
        log.info("Info log");//не критичная информация
        log.warn("Warn log");
        log.error("Error log");//логируем ошибки
        log.fatal("Fatal log");

        new Thread(() -> log.info("Сообщение из потока")).start();

        try {
            throw new RuntimeException("AAAAAAAAAAAAAA");
        } catch (RuntimeException e) {
            log.throwing(e);//чтобы нормально логировать исключение используем throwing(e)
        }
    }
}