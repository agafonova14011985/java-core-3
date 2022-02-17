package ru.geekbrains.jc3_feb.l2_jdbc;
import java.sql.*;

public class DatabaseExample {
    private static Connection connection; //инициализация объекта
    private static Statement statement;
    private static PreparedStatement ps;//подготовленный запрос при подключения к БД, ожидаем что мы обратимся к запросу и передадим аргументы
    private static String insertStatement = "insert into students (name, score) values (?, ?);";
    private static final String EXAMPLE_CALL = "{call do_something_prc(?,?,?)}";
    private static final String DB_CONNECTION_STRING = "jdbc:sqlite:java1.db";//записала на прямую в методе конект /java1.db - путь к БД
    private static final String CREATE_REQUEST = "create table if not exists students " +
            "(id integer primary key autoincrement, name text, score integer);";//если такой таблице нет то создаем табл. студентов
    private static final String DROP_REQUEST = "drop table if exists students;";
    private static final String SIMPLE_INSERT_REQUEST =
            "insert into students (name, score) values ('Vasya Pupkin', 80), ('Kolya Morzhov', 90), ('Vitaly Petrov', 100);";
    //вставляем в таблицу имя и успеваемость, и их параметры

    public static void main(String[] args)  {
        try {
            connect();
            createTable();
          //  simpleInsertExample();
          //  dropExample();
            //simpleUpdateExample();
          //  notReallyCorrectInsertExample("Radamir", 80);
          //  preparedStatementExample("Pavel", 100);
          //  simpleReadExample();
          //  massInsertExample();
          //  massInsertTransactionalExample();
           // massInsertBatchExample();
//            var callableStatement = connection.prepareCall(EXAMPLE_CALL);//вызов процедуры базы данных/метод
//            callableStatement.setString(1, "safdvad");
//            callableStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }
//пакетная отправка данных//лучшый вариант при запросе по сети /меньше тратиться времени
    private static void massInsertBatchExample()  throws SQLException  {
        var start = System.currentTimeMillis();
        connection.setAutoCommit(false);
        for (int i = 0; i < 10; i++) {
            ps.setInt(2, i);
            ps.setString(1, "Student #" + i + 1);
            ps.addBatch();//добавляет запрос в пакет
        }
        ps.executeBatch();//выполнил пакетный запрос
        connection.setAutoCommit(true);
        System.out.println(System.currentTimeMillis() - start);
    }
//объединение записей в одну транзакцию , перед внесеня записей выключим автокомит//экономия времени
    private static void massInsertTransactionalExample()  throws SQLException  {
        var start = System.currentTimeMillis();
        connection.setAutoCommit(false);//выключение автокомита, запросы идут в одной транзакции
        for (int i = 0; i < 10; i++) {
            preparedStatementExample("Student #" + i + 1, i);
        }
//        connection.commit(); //Делает коммит, не включает автокоммит
        connection.setAutoCommit(true);//вкл авто комит
        System.out.println(System.currentTimeMillis() - start);
    }
//добавление большого количества студентов и засечем сколько времени это займет
    private static void massInsertExample()  throws SQLException  {
        var start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            preparedStatementExample("Student #" + i + 1, i);
        }
        System.out.println(System.currentTimeMillis() - start);
    }
// для безопасности, подготовленный запрос/ защищает от инжекций
    private static void preparedStatementExample(String name, int score)  throws SQLException  {
        ps.setString(1, name);
        ps.setInt(2, score);
        ps.executeUpdate();//метод для вывода, возвращает кол-во сторок , которые были обновленны
    }
//получает имя и успеваемость и вставлять//опасно
    private static void notReallyCorrectInsertExample(String name, int score)  throws SQLException  {
        var count = statement.executeUpdate("insert into students (name, score) values (\'" + name + "\', " + score + ");");
        System.out.printf("Updated %d rows\n", count);
    }
//чтение записей из БД и печать их в консоль
    private static void simpleReadExample()  throws SQLException  {
        try (var resultSet = statement.executeQuery("select * from students order by name desc;")) {
            while (resultSet.next()) {
                System.out.printf("Student id: %d, name: %s, score: %d\n",
                        resultSet.getInt(1),//resultSet.getInt - возвращает нужное значение/по индексу начиная с 1
                        resultSet.getString("name"),
                        resultSet.getInt("score"));
            }
        }
    }
//изменяем данные в таблице/ имя /для конкретики where score > 90;" / там где больше 90
    private static void simpleUpdateExample()  throws SQLException  {
        var count = statement.executeUpdate("update students set name = 'Alex' where score > 90;");
        System.out.printf("Updated %d rows\n", count);
    }
//удаляем таблицу ее данные
    private static void dropExample()  throws SQLException  {
        statement.execute(DROP_REQUEST);
    }
//добавление студунтов
    private static void simpleInsertExample()  throws SQLException  {
        var count = statement.executeUpdate(SIMPLE_INSERT_REQUEST);
        System.out.printf("Inserted %d rows\n", count);
    }
//создание таблицы
    private static void createTable() throws SQLException {
        statement.execute(CREATE_REQUEST); // что бы выполнить любой запрос нужен statement /execute - говорят получилось или нет ,
    }

    private static void connect() throws SQLException   {
//        Раньше требовалось проинициализировать драйвер
//        try {
//            Class.forName("org.sqlite.JDBC"); //Class.forName -грузит класс в память , но нужно ловить исключения, когда класс загрузится  то статические методы инициализации загрузятся в память
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        connection = DriverManager.getConnection("jdbc:sqlite:java1.db");//
        statement = connection.createStatement();//инициализация абстракции интерфейс, для  статических ыкл запросов
        ps = connection.prepareStatement(insertStatement);//подготовленный запрос
    }


    private static void disconnect() {
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (ps != null) ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}