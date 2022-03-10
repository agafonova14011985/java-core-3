package ru.geekbrains.jc3_feb.l7_reflection.home_work;

/*
                    Урок 7. Reflection API и аннотации
1. Создать класс, который может выполнять «тесты», в качестве тестов выступают классы с наборами методов с аннотациями @Test.
Для этого у него должен быть статический метод start(), которому в качестве параметра передается или объект типа Class,
или имя класса. Из «класса-теста» вначале должен быть запущен метод с аннотацией @BeforeSuite, если такой имеется,
далее запущены методы с аннотациями @Test, а по завершению всех тестов – метод с аннотацией @AfterSuite.
К каждому тесту необходимо также добавить приоритеты (int числа от 1 до 10),
в соответствии с которыми будет выбираться порядок их выполнения, если приоритет одинаковый,
то порядок не имеет значения. Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в единственном экземпляре,
иначе необходимо бросить RuntimeException при запуске «тестирования».
 */


import ru.geekbrains.jc3_feb.l7_reflection.ReflectionExample;

public class Main {
    public static void main(String[] args) {

        ClassForTesting cl = new ClassForTesting();
        TestsHandler.start(cl.getClass());
        System.out.println("\nИсследование\n");
        Reflection.classInfo(String.class);
    }
}

////1 способ
//    //        Class<Cat> catClass = Cat.class;//нужно получить объект типа класс
//    //можно обратиться Cat.class
//
//    //2 способ
//    //или можно получить объект так с помощью forName + референс на класс
////        Class catClass = Class.forName("ru.geekbrains.jc3_feb.l7_reflection.ReflectionExample$Cat");//$ разделитель для вложенного класа
//
//    //3 способ, при создании ката
//    ReflectionExample.Cat cat = new ReflectionExample.Cat("Murzik", "red", 2);
//
////        ru.geekbrains.jc3_feb.l3_io.Cat  cat = new ru.geekbrains.jc3_feb.l3_io.Cat ("Murzik", "red");
//
//    //получение объекта тапа класса getClass
//    Class catClass = cat.getClass();
