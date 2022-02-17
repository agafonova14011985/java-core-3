package ru.geekbrains.jc3_feb.l2_jdbc1;

import java.sql.*;

public class JdbcApp {
    private static Connection connection;
    private static Statement stmt;

    public static void main(String[] args) {
        try {
            connect();
            createTableEx();
          //  dropTableEx();
             readEx();
            clearTableEx();
            deleteEx();
            updateEx();
            insertEx();


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            disconnect();
        }
    }


    public static void connect() throws SQLException{
        connection = DriverManager.getConnection("jdbc:sqlite:java.db");
        stmt = connection.createStatement();
    }


    public static void disconnect() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void createTableEx() throws SQLException {
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS students (\n" +
                "        id    INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "        name  TEXT,\n" +
                "        score INTEGER\n" +
                "    );");

    }

    private static void dropTableEx() throws SQLException {
        stmt.executeUpdate("DROP TABLE IF EXISTS students;");
    }

    private static void readEx() throws SQLException {
        try (ResultSet rs = stmt.executeQuery("SELECT * FROM students;")) {
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString("name") + " " + rs.getInt(3));
            }
        }
    }

    private static void clearTableEx() throws SQLException {
        stmt.executeUpdate("DELETE FROM students;");
    }

    private static void deleteEx() throws SQLException {
        stmt.executeUpdate("DELETE FROM students WHERE name = 'Bob1';");
    }

    private static void updateEx() throws SQLException {
        stmt.executeUpdate("UPDATE students SET score = 100 WHERE name = 'Bob4';");
    }

    private static void insertEx() throws SQLException {
        stmt.executeUpdate("INSERT INTO students (name, score) VALUES ('Bob4', 60);");
    }




}

