package ru.geekbrains.jc3_feb.l1_generics.home_work;

/*   Практическая 1       *
1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
2. Написать метод, который преобразует массив в ArrayList;
3. Большая задача:
a. Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)
b. Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта,
поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
c. Для хранения фруктов внутри коробки можете использовать ArrayList;
d. Сделать метод getWeight() который высчитывает вес коробки,
зная количество фруктов и вес одного фрукта(вес яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах);
e. Внутри класса коробка сделать метод compare,
который позволяет сравнить текущую коробку с той,
которую подадут в compare в качестве параметра,
true - если их веса равны, false в противном случае(коробки с яблоками мы можем сравнивать с коробками с апельсинами);
f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку
(помним про сортировку фруктов, нельзя яблоки высыпать в коробку с апельсинами),
соответственно в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке;
g. Не забываем про метод добавления фрукта в коробку.
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeWorkLesson1 {

    public static void main(String[] args) {

        //1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
        elementReplacement();

        //2. Написать метод, который преобразует массив в ArrayList;
        arrayConversion();

        // 3. Задание
        fruitsInBox();



    }

    // 3. Задание
    private static void fruitsInBox() {

        System.out.println("\n3. Большая задача: ");
        Apple apple1 = new Apple();//создаем объекты яблоки
        Apple apple2 = new Apple();


        Orange orange1 = new Orange();//создаем объекты апельсины
        Orange orange2 = new Orange();
        Orange orange3 = new Orange();
        Orange orange4 = new Orange();

        Box<Apple> box1 = new Box<Apple>(apple1, apple2);//в первую коробку помещаем яблоки
        Box<Orange> box2 = new Box<Orange>(orange1, orange2, orange3, orange4);//во вторую апельсины

        System.out.println("Количество яблок в коробке: " + (box1.getItems().size()) + "шт.");
        System.out.println("Количество апельсинов в коробке: " + (box2.getItems().size()) + "шт.");
        System.out.println("Вес коробки с яблоками: " + (box1.getWeight()));
        System.out.println("Вес коробки с апельсинами: " + (box2.getWeight()));
        System.out.println("Сравнение коробок с яблоками и апельсинами: " + box1.compare(box2));
        System.out.println("Сравнение коробок с яблоками и яблоками : " + box1.compare(box1));
        System.out.println("Сравнение коробок с апельсинами и апельсинами : " + box2.compare(box2));




    }

    //2. Написать метод, который преобразует массив в ArrayList;
    private static void arrayConversion() {
        System.out.println("\nЗадание 2: Написать метод, который преобразует массив в ArrayList; ");
        //массив из строк
        String[] arr = {"Aa", "Bb", "Cc", "Dd"};
        System.out.println("Массив из строк: " + Arrays.toString(arr));
        List<String> listStrings = new ArrayList<String>();

        // возвращает массив в виде списка, /если массив будет изменен, то будет изменен и его список
        listStrings = Arrays.asList(arr);
        for (String str : listStrings)
    System.out.print(" " + str);
        System.out.println(" - Полученный ArrayList ");
    }

    //1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
    private static void elementReplacement() {
        //числа
        System.out.println("Задание 1: Написать метод, который меняет два элемента массива местами.");
        List<Double> list1 = Arrays.asList(1d, 2d, 3d);
        System.out.println("Массив первоначальный: " + list1);
        swapped(list1);
        System.out.println("Массив с изменениями : " + list1);

        //строки
        List<String> list2 = Arrays.asList("A", "B", "C", "D");
        System.out.println("Массив первоначальный: " + list2);
        swapped(list2);
        System.out.println("Массив с изменениями : " + list2);



    }

    public static <T> List<T> swapped(List<T> list) {
        List<T> tempElement = list;


        T firstElement = tempElement.get(0);
        T lastElement = tempElement.get(list.size() - 1);

        //используем .set для замены элементов с первого индекса на последний элемент
        tempElement.set(0, lastElement);
        //смещение начальных элементов в конец массива
        tempElement.set(tempElement.size() -1, firstElement);
        return tempElement;//возвращаем значение


    }




}
