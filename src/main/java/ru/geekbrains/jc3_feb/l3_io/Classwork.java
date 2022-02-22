package ru.geekbrains.jc3_feb.l3_io;


/*
                   Практическая 3
1. Добавить в сетевой чат запись локальной истории в текстовый файл на клиенте.
2. После загрузки клиента показывать ему последние 100 строк чата.
 */

//библиотека  потоков ввода вывода
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Classwork {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

//       filesExample();
//        simpleFileFileWriteExample();
//        simpleReadExample();
//        bufferingExample();
//        readExample();
//        sequenseExample();
//       rafExample();

//        serializationExample();
//        externalizableExample();

        xxx();

    }

    //
    private static void xxx() throws IOException {
        try (var fis = new FileInputStream("test/1/2.txt")) {//считываем файл
            try (var zos = new ZipOutputStream(new FileOutputStream("test/zipped.zip"))) {//записываем файл

                var buf = new byte[512];
                zos.putNextEntry(new ZipEntry("test/1/2.txt"));

                int x;//кол во байт списанных
                while ((x = fis.read(buf)) > 0) {//читаем

                    zos.write(buf, 0, x);//в зос пишем
                }

            }
        }
    }

//полный контроль над сериализацией и десириализации/++/явно приписываем что получаем и возвращаем
    private static void externalizableExample() throws IOException, ClassNotFoundException {
        var cat = new CatEx("Barsik", "purple");//создание
//        //записался и создался файл
//изменить не изменять много вариантов контроля
        try (var ois = new ObjectInputStream(new FileInputStream("test/barsik"))) {
            var deserializedCat = (CatEx) ois.readObject();
            System.out.println(cat);
            System.out.println(deserializedCat);
            System.out.println(cat == deserializedCat);
            System.out.println(cat.equals(deserializedCat));
        }
    }
//превращение объектов в байты //превращение байтов в объекты
    private static void serializationExample() throws IOException, ClassNotFoundException {
        Cat cat = new Cat("Murzik", "purple");//создание кота
        //превратим в байты
 //       try (var oos = new ObjectOutputStream(new FileOutputStream("test/murzik"))) {
 //          oos.writeObject(cat);//и запишу в файл
 //       }

        try (var ois = new ObjectInputStream(new FileInputStream("test/murzik"))) {
            var deserializedCat = (Cat) ois.readObject();//(Cat)  приведение типов
            System.out.println(cat);//кот
            System.out.println(deserializedCat);
            System.out.println(cat == deserializedCat);
            System.out.println(cat.equals(deserializedCat));//сравнение
        }
    }
//
    private static void rafExample() throws IOException {
        try (var raf = new RandomAccessFile("test/1/1.txt", "r")) {//"r")-только для чтения, из бибил
            raf.seek(190);//seek-перемещение на позицию в байтих
            int x;
            while ((x = raf.read()) > -1) {
                System.out.println((char) x);
            }
        }
    }

    private static void sequenseExample() throws IOException {
        File file1 = new File("test/1/1.txt");//создание файла
        File file2 = new File("test/1/2.txt");
        File file3 = new File("test/1/3.txt");
        if (!file1.exists()) {//если файл  не существует,
           file1.createNewFile();//то создадим новый файл
        }
        if (!file2.exists()) {//если файл  не существует,
            file2.createNewFile();//то создадим новый файл
        }
        if (!file3.exists()) {//если файл  не существует,
            file3.createNewFile();//то создадим новый файл
        }
        var streams = new ArrayList<InputStream>();
        streams.add(new FileInputStream("test/1/1.txt"));//добавляем файл
        streams.add(new FileInputStream("test/1/2.txt"));
        streams.add(new FileInputStream("test/1/3.txt"));

        //передаем в SequenceInputStream все стримы  более 2 стримов
        var sis = new SequenceInputStream(Collections.enumeration(streams));

        int x;
        //чтение до конца
        while ((x = sis.read()) != -1) {
            //печать символы
            System.out.print((char) x);
        }
    }
//чтение буферами
    private static void readExample() throws IOException {
//        try (var br = new BufferedReader(new FileReader("test/ex3.txt"))) {
//            String line;//чтение по строкам
//           while ((line = br.readLine()) != null) {//чтение строки не нуль, читаем не пустую строку
//               System.out.println(line);
//            }
//        }
        try (var br = new BufferedReader(new FileReader("test/ex3.txt"))) {
           var list = List.of("jkfdv");
           list.stream();

////
            var count = br.lines().distinct().count();
           System.out.println(count);


          // br.lines()
//            //        .distinct()//избавление строк дубликатов
//                    .filter(s -> s.contains("s"))
//                   .map(s -> s.length())//изменяет тип данных/
//                   .flatMap(s -> Stream.of(s.getBytes(StandardCharsets.UTF_8)))//flatMap-превращает каждый элемент в несколько элементов
//                    .forEach(System.out::println);


       }
    }

    private static void bufferingExample() throws IOException {
        //сколько времени потратится что бы прочитать файл по байтам
        //приложение обращалось в ОС, и просила побайтово прочитать файл/долго если большой файл

 //               var startTime = System.currentTimeMillis();
 //       try (var fis = new FileInputStream("test/ex3.txt")) {
 //           int x;
 //           while ((x = fis.read()) > -1) {}
  ////      }
    //    System.out.println(System.currentTimeMillis() - startTime);


//        //чтение байтов пачками из файла/достумм к данным быстрей
//        var startTime = System.currentTimeMillis();
////        var buf = new byte[8];//быстрее в 8 раз
//       var buf = new byte[512];//быстрее в 512 раз
//        try (var fis = new FileInputStream("test/ex3.txt")) {
//            int x;
//           while ((x = fis.read(buf)) > -1) {//метод возвращает количество байтов которое было считанно
//           }
//        }
//        System.out.println(System.currentTimeMillis() - startTime);
//
//       var startTime = System.currentTimeMillis();
//       try (var bis = new BufferedInputStream(new FileInputStream("test/ex3.txt"))) {//создаем буферизованный файловый поток из файлового потока
//            int x;
//            while ((x = bis.read()) > -1) {
//            }
//       }
//       System.out.println(System.currentTimeMillis() - startTime);
    }

//чтение файла +InputStream
    private static void simpleReadExample() throws IOException {
        try (var fis = new FileInputStream("test/ex2.txt")) {//расширение не обязательно txt
            int x;
            while ((x = fis.read()) > -1) {//чтение файла по байтово/ read возвращает инты -1) - чтение до конца
                System.out.print((char) x);//печатаеем x
            }
        }
    }
//потоки ввода вывода позволяют работать с потоками, считывать....
    private static void simpleFileFileWriteExample() throws IOException {
        var s = "Hello world!";
        var file = new File("test/ex1.txt");
        if (!file.exists()) {
            file.createNewFile();//если файла нет то его нужно создать
        }
        try (var fos = new FileOutputStream("test/ex1.txt")) {
            fos.write(s.getBytes(StandardCharsets.UTF_8));//запись байтов, есть множество реализация , строку превращаем в байты
//            for (int i = 0; i < 200; i++) {
//                fos.write(i);//запись символов, не читается
//            }
        }
    }

//    private static void filesExample() throws IOException {
//        //инициализация файла, создание объекта тип файл, абстрактное представление, хранит путь к файлу или директории
//         File file = new File("test");
//        System.out.println(file.exists());//проверяем существует ли этот файл вообще
//        System.out.println(file.isFile());//проверка является ли он файлом
//        System.out.println(file.isDirectory());//проверка является ли он директорией
//
//        var file2 = new File("test/1.txt");
//        if (!file2.exists()) {//если файл 2 не существует,
//           file2.createNewFile();//то создадим новый файл
//        }
//        System.out.println(file2.delete());//удаляем файл
//
//
//        var file3 = new File("test/1");//объявление
//        System.out.println(file3.mkdir());//создание каталога/если еще раз запустить то каталог не создаться так как он уже существует
//
//        var file4 = new File("test/1/2/3/4/5/6/7/8/9/10");//создание каталога с множеством вложения
//        System.out.println(file4.mkdirs());//но он не создает, так как mkdir()он создает только один каталог/но если mkdirs() то создаст множество

//        var file5 = new File("test/1/5423");//объявление и имя
//        file5.renameTo(new File("test/1/2/3/4/5/6/7/8/9/10/5423"));//переименование файл в другой файл = перемещение

//        var file6 = new File("test");
//        var fileNames = file6.list();//получение списка файлов//возвращает массив строк
//        var fileNames = file6.list(((dir, name) -> name.endsWith("1")));//фильтр
//        for (String fileName : fileNames) {
//            System.out.println(fileName);
//        }

        //возвращает объект типа файл, фильтрация не только по имени но и по типу
//        var files = file6.listFiles();
//        for (File file : files) {
//            System.out.println(file.getName());
//        }
//
//        System.out.println(file2.getParent());//получение имени родительского каталога
//        recursiveFileWalkAndPrint(file6);//обходит только 6 файл
//       recursiveFileWalkAndPrint(new File("src"));//обходит каталог src
//   }

    private static void recursiveFileWalkAndPrint(File file) {
        //обойдем все каталоги
        //если файл это файл то выдаст имя
        if (file.isFile()) {
            System.out.println("File -->> " + file.getPath());
            //если это каталог, то выдаст каталоги
        } else {
            System.out.println("Catalog -->> " + file.getPath());
            //зайдем в него и достанем все файлы, пройдемся по файлам и вызовем метод
            var files = file.listFiles();
            for (File innerFile : files) {
                recursiveFileWalkAndPrint(innerFile);
            }
        }
    }
}