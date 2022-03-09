package ru.geekbrains.jc3_feb.l7_reflection;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LittleHiber {
    private static Connection connection;

    ////передача в метод некий объект и он добавлялся в БД
    public static <T> void insertObject(T o) {
        if (!o.getClass().isAnnotationPresent(Table.class)) {
            throw new RuntimeException("Class not annotated with @Table");
        }

        Field[] fields = o.getClass().getDeclaredFields();//все поля

        try {
            connect();
            // insert into cats (name, color) values ('ddd', 'dssdf');;

            var sb = new StringBuilder("insert into ");
            //имя таблици получаем из анотации
            sb.append(o.getClass().getAnnotation(Table.class).name());
            sb.append(" (");
            int questionsCount = 0;//
            var values = new LinkedList<>();//будут объекты
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class)) {
                    if (field.getAnnotation(Id.class).autoincrement()) {
                        continue;//пропускаем поле и ни чего не делаем
                    }
                }

                if (field.isAnnotationPresent(Column.class)) {
                    var colName = field.getAnnotation(Column.class).name().isBlank() ?
                            field.getName() : field.getAnnotation(Column.class).name();
                    sb.append(colName);
                    sb.append(", ");
                    questionsCount++;//если добавляли то ++
                    values.add(field.get(o));//добавляем
                }
            }

            sb.setLength(sb.length() - 2);//удаляем последнию запятую и пробел
            sb.append(") values (");

            //проставляем вопроссы
            for (int i = 0; i < questionsCount; i++) {
                sb.append(i + 1 == questionsCount ? "?);" : "?, ");
            }

            try (var stmt = connection.prepareStatement(sb.toString())) {
                var num = 1;
                for (Object value : values) {
                    stmt.setObject(num++, value);
                }
                stmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    //таблица на основе класса
    public static <T> void createTable(Class<T> c) {
        //перевод типов
        var typeMap = getTypeMap();

        //если у класса нет анотации то выкинем исключения
        if (!c.isAnnotationPresent(Table.class)) {
            throw new RuntimeException("Class not annotated with @Table//нет анотации не генерим");
        }

        //достаем все поля
        Field[] fields = c.getDeclaredFields();
        var hasId = false;

        //пробежим по всем полям и если у поля isAnnotationPresent(Id.class)
        //то
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                if (hasId) {
                    throw new RuntimeException("Only one primary key needed // нет ");
                }
                hasId = true;
            }
        }

        try {
            //подключаемся
            connect();
            //нужен стейтмент
            try (var smt = connection.createStatement()) {
               //нужен опред запрос
                // create table cats (id integer primary key autoincrement, name text, color text);

                //
                var sb = new StringBuilder("create table ");
                //апендим имя таблицы
                sb.append(c.getAnnotation(Table.class).name());
                sb.append(" (");
                //перечисляем имена полей и их типы данных через запятую
                //перебераем поля и если
                for (Field field : fields) {
                    field.setAccessible(true);
                    //если у поля есть аннотация Column , то
                    if (field.isAnnotationPresent(Column.class)) {//если оно пустое то
                        var colName = field.getAnnotation(Column.class).name().isBlank() ?

                                field.getName() : field.getAnnotation(Column.class).name();
                        sb.append(colName);
                        sb.append(" ");
                        //тип данных получаем из мапы
                        sb.append(typeMap.get(field.getType()));

                        //если был еще и Id, то добавляем
                        if (field.isAnnotationPresent(Id.class)) {
                            sb.append(" primary key ");
                            //если был автоинкремент то
                            if (field.getAnnotation(Id.class).autoincrement()) {
                                //апендим еще пробел и автоинкремент
                                sb.append(" autoincrement ");
                            }
                        }//в конце
                        sb.append(", ");
                    }
                }//удаляем последний пробел и запятую
                sb.setLength(sb.length() - 2);
                sb.append(");");//закрываем скобку
                smt.execute(sb.toString());
            }
        } catch (Exception e) {//ловим все исключения
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    private static Map<Class, String> getTypeMap() {
        Map<Class, String> typeMap = new HashMap<>();
        //перевод значений джавы в скулайт
        typeMap.put(int.class, "integer");
        typeMap.put(String.class, "text");
        return typeMap;
    }

    private static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/reflect.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
