package ru.geekbrains.jc3_feb.l7_reflection;

import ru.geekbrains.jc3_feb.l3_io.Cat;

import java.lang.reflect.*;

public class ReflectionExample {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {

        //1 способ
        //        Class<Cat> catClass = Cat.class;//нужно получить объект типа класс
        //можно обратиться Cat.class

        //2 способ
        //или можно получить объект так с помощью forName + референс на класс
//        Class catClass = Class.forName("ru.geekbrains.jc3_feb.l7_reflection.ReflectionExample$Cat");//$ разделитель для вложенного класа

        //3 способ, при создании ката
        Cat cat = new Cat("Murzik", "red", 2);

//        ru.geekbrains.jc3_feb.l3_io.Cat  cat = new ru.geekbrains.jc3_feb.l3_io.Cat ("Murzik", "red");

        //получение объекта тапа класса getClass
        Class catClass = cat.getClass();

        //могут быть:
//        Class - объекты типа классы
//        Field - объекты типа поля
//        Constructor - объекты типа конструкторы
//        Method - объекты типа методов

        //печатает полное имя + пакеты
//        System.out.println(catClass.getName());
        //только имя класса
//        System.out.println(catClass.getSimpleName());

        //модификаторы можем запросить у класса метода, конструктора....
        //модификаторы выгружаются в виде инта методом getModifiers
//        int modifiers = catClass.getModifiers();
//
        //распечатка модификатора
//        System.out.println(modifiers);
        //
//        System.out.println(Modifier.toString(modifiers));
        //проверка наличие определенного модификатора
//        System.out.println(Modifier.isAbstract(modifiers));

//        System.out.println(Modifier.isPrivate(modifiers));
        //выдаст тру
//        System.out.println(Modifier.isPublic(modifiers));
//        System.out.println(Modifier.isStatic(modifiers));
//        System.out.println(Modifier.isFinal(modifiers));

        //можем достать поля, в виде массива из кота класса методом getFields
//        //getFields - возвращает только публичные поля
//        Field[] fields = catClass.getFields();

        //дастает все поля и приват и публик
        Field[] fields = catClass.getDeclaredFields();
//
//        for (Field field : fields) {
        //печать полей
//            System.out.println(field);
//        }

        //можно декомпелировать только лишь одно поля по имени но могут быть исключение если такого поля нет
//        Field field = catClass.getDeclaredField("name");

//        Field fieldStatic = catClass.getDeclaredField("type");
        //для напечатания поля вытаскиваем его из объекта
//        System.out.println(field.get(cat));
        //а его получить из объекта налл//для статики
//        System.out.println(fieldStatic.get(null)); //для статики
//

//        field.setAccessible(true);
        //можем поменять значение
        //может случится исключение, но с field.setAccessible(true); доступ есть
//        field.set(cat, "Brb");
//        System.out.println(field.get(cat));


//        var someField = catClass.getDeclaredField("priv");
//        someField.setAccessible(true);
//        someField.set(cat, "BBBB");
//        System.out.println(someField.get(cat));

//        var someField = catClass.getDeclaredField("type");
//        someField.setAccessible(true);
//        someField.set(null, "BBBB");//не сможет поменять так как статика
//        System.out.println(someField.get(null));

        //можем достать массив конструктора
//        Constructor[] constructors = catClass.getConstructors();
//        Constructor[] constructors = catClass.getDeclaredConstructors();
//        for (Constructor constructor : constructors) {
//            System.out.println(constructor);
//        }
        //отличается только нобору параметров//получаем конструктор и можем с ним что то делать
//        Constructor<Cat> constructor = catClass.getDeclaredConstructor(String.class, String.class, int.class);
////<Cat> - привидение типов

        //из конструктора можно создать кота из конструктора методом newInstance
//        Cat reflector = constructor.newInstance("REFLECTOR", "BLACK", 999);
////        catClass.newInstance();
        //получился новый кот
//        System.out.println(reflector);

        //доступ к методам
//        Method[] methods = catClass.getDeclaredMethods();//для всех
        //объявление конкретного метода//указываем имя и
//        var methRun = catClass.getDeclaredMethod("run", int.class);
        //открываем доступ если не публик
//        methRun.setAccessible(true);
        //и вызовем его методом:
//        methRun.invoke(cat, 999);

        for (Field field : fields) {
            field.setAccessible(true);
            //проверка есть ли у поля анотация
            if (field.isAnnotationPresent(MyAnnotation.class)) {
               //то распечатаем значение дате
                System.out.println(field.getAnnotation(MyAnnotation.class).data());
            }
        }
    }

    @MyAnnotation()
    public static final class Cat {
        @MyAnnotation(data = "jksnvfdjdf")
        public final String some = "AAAA";
        static final String type = "CAT";
        public final String name;
        public String color;
        private int privateField;
        final int age = 1;
        private Bowl b;

        public Cat() {
//           age = 1;
            name = "Nameless";
        }

        //        @MyAnnotation(data = "jksnvfdjdf")
        public Cat(String name, String color, int age) {
            this.name = name;
            this.color = color;
//            this.age = age;
        }

        void voice() {
            System.out.println(name + " mew");
        }

        private void run(int distance) {
            System.out.println(name + " running for " + distance);
        }

        @Override
        public String toString() {
            return "Cat{" +
                    "name='" + name + '\'' +
                    ", color='" + color + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static class Bowl {
        int food;
    }
}