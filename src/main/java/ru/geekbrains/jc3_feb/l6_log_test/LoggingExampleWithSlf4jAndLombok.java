package ru.geekbrains.jc3_feb.l6_log_test;

import lombok.extern.slf4j.Slf4j;//пользуемся интерфейсом но под капотом все то же
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Slf4j//анотация можно смотреть документацию, если она висит то при компиляции формируется опред строка
public class LoggingExampleWithSlf4jAndLombok {

    public static void main(String[] args) {

        double d = .0324;
        String s = "Hi";

        log.info("Number = {} and string = {}", d, s);

        log.trace("Trace log");
        log.debug("Debug log");
        log.info("Info log");
        log.warn("Warn log");
        log.error("Error log");

        new Thread(() -> log.info("Another thread")).start();

        try {
            throw new RuntimeException("AAAAAAAAAAAAAA");
        } catch (RuntimeException e) {
            log.error("an error occured", e);
        }
    }
}
