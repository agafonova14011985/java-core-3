package ru.geekbrains.jc3_feb.l1_generics;

import java.util.Collections;
import java.util.List;

/*
при создании объектов класса указывают тип данных которые там будут хранится

 */
 //Generics - обобщение/можно указать тип данных /избавляет явного приведения типов и много проверки
//что бы бало ясно что это обобщенный тип в сигнатуре указываем диамонт  и в них название наших условных типов 1 или несколько
//можно ограничевать обобщенеи сверху или снизу super или extends
public class GenericsExample {
    public static void main(String[] args) {
//        simpleBoxExample();
//        genericBoxExample();
//        numbersExample();
//        comparingFloats();

        var ints1 = List.of(1, 2, 44);//первый список
        var ints2 = List.of(231, 2, 44);//второй список

        //получим сумму первых элементов этих списков / но только после приведения ((Integer)
        System.out.println((Integer) getfirstObject(ints1) + (Integer) getfirstObject(ints2));

        //здесь приведение типов не нужно
        System.out.println(getfirstObjectGen(ints1) + getfirstObjectGen(ints2));

//        Collections.copy();

    }
 //обобщенный метод возвращает тип данных Т ,
 // будет получать данный обьект из списка с данными типа Т
    private static <T> T getfirstObjectGen(List<T> list) {
        return list.get(0);//возвращает первый объект из списка
    }

    //не обобщенный метод
    private static Object getfirstObject(List list) {
        return list.get(0);//возвращает первый объект из списка
    }

    private static void numbersExample() {
        var boxI = new BoxWithNumbers<>(2313, 123, 21312, 12321);
        //или  var boxК = new BoxWithNumbers<>(new Integer[]{32,34});

        var boxD = new BoxWithNumbers<>(2313.0, 123.0, 21312.0, 12321.0);
//        var boxS = new BoxWithNumbers<String>("1000.0"); //стринги не работают так как есть ограничения extends Number

        System.out.println(boxI.avg());
        System.out.println(boxD.equalsByAvg(boxI));
//        boxI.setNumbers(new Float[] {1f, 2f});//не можем в интежер положить флоты
    }

    //показывает метод, что флоаты сравнивать проблематично
    private static void comparingFloats() {
        var a = 0.7;
        var b = 0.0;

        for (int i = 0; i < 70; i++) {
            b += 0.01;
        }

        System.out.println(a == b);
        System.out.println("A = " + a + " B = " + b);
    }

    private static void genericBoxExample() {


        var boxInt1 = new GenericBox<>(100500);//при создании коробки на этом этапе можем сказать что эта коробка для чисел, другая для строк и они не перепутаются
        var boxInt2 = new GenericBox<>(1);
        var boxString1 = new GenericBox<>("Hello ");
        var boxString2 = new GenericBox<>("world!");

//        GenericBox<Integer> box = new GenericBox<>(21); // полная церемония//8 джава
//        GenericBox box = new GenericBox(21); Raw use//уже не нужен

        //many code strings
//        boxInt1.setObj("hello");//если вася положит строку в коробе с числами то компилятор не разрешит
//        boxInt1.setObj(2);
//        boxString1.setObj(324);
        //many code strings

        //не нужно приводить типы, это делает компилятор, не нужна проверка на  instanceof

        //без каста считает сумма!!!!!
        var sum = 0;
        sum = boxInt1.getObj() + boxInt2.getObj();

        System.out.println(sum);
    }

    private static void simpleBoxExample() {

        var boxInt1 = new Box(100500);//коробка с числами
        var boxInt2 = new Box(1);
        var boxString1 = new Box("Hello ");//коробка сс строками
        var boxString2 = new Box("world!");

        //many code strings//много кода

        //кто то хочет поменять содержимое коробок
        //компилятор выдаст ошибку
        boxInt1.setObj("hello");
        //many code strings
        var sum = 0;//инициализация локальной переменной
        //сложение коробок только после выполнения условия /явное привидение типов /каст
        if (boxInt1.getObj() instanceof Integer && boxInt2.getObj() instanceof Integer) {

            //складываем коробки, но только после явного приведения типов
            sum = (Integer) boxInt1.getObj() + (Integer) boxInt2.getObj();
        }

        System.out.println(sum);//вывод результата
    }
}