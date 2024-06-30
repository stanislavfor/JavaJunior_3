package org.example;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Homework {

    /**
     * С помощью JDBC, выполнить следующие пункты:
     * 1. Создать таблицу Person (скопировать код из класса JDBC)
     * 2. Создать таблицу Department (id bigint primary key, name varchar(128) not null)
     * 3. Добавить в таблицу Person поле department_id типа bigint (внешний ключ)
     * 4. Написать метод, который загружает Имя department по Идентификатору person
     * 5. * Написать метод, который загружает Map<String, String>, в которой маппинг person.name -> department.name
     *   Пример: [{"person #1", "department #1"}, {"person #2", "department #3}]
     * 6. ** Написать метод, который загружает Map<String, List<String>>, в которой маппинг department.name -> <person.name>
     *   Пример:
     *   [
     *     {"department #1", ["person #1", "person #2"]},
     *     {"department #2", ["person #3", "person #4"]}
     *   ]
     *
     *  7. *** Создать классы-обертки над таблицами, и в пунктах 4, 5, 6 возвращать объекты.
     */

    public static void main(String[] args) {

        // Подключение к базе данных H2
        String url = "jdbc:h2:mem:test";
        String user = "sa";
        String password = "";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            // Создание таблиц в заполнение Параметров
            createDepartmentTable(connection);
            createPersonTable(connection);
            insertDepartments(connection);
            insertPersons(connection);
            // Вывод результата методов
            System.out.println("СПИСКИ");
            Map<String, Optional<String>> personDepartments = getPersonDepartments(connection);
            System.out.println("Специалисты (по Отделам) : \n" + formatPersonDepartments(personDepartments));
            Map<String, List<String>> departmentPersons = getDepartmentPersons(connection);
            System.out.println("Отделы (с указанием специалистов) : \n" + formatDepartmentPersons(departmentPersons));
            long id = 1;
            Optional<String> departmentName = getPersonDepartmentName(connection, id);
            System.out.println("Специалист с id=" + id + " работает в Отделе : \n" + departmentName.orElse("Not found"));

        } catch (SQLException e) {
            System.err.println("Во время создания таблицы произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 1. Создать таблицу Person (скопировать код из JDBC)
     * 3. Добавить в таблицу Person поле department_id типа bigint (внешний ключ)
     */
    private static void createPersonTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE TABLE Person (
                    id BIGINT PRIMARY KEY,
                    name VARCHAR(256),
                    age INTEGER,
                    active BOOLEAN,
                    department_id BIGINT
                )
            """);
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы Person : " + e.getMessage());
            throw e;
        }
    }

    /**
     * 2. Создать таблицу Department (id bigint primary key, name varchar(128) not null)
     */
    private static void createDepartmentTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE TABLE Department (
                    id BIGINT PRIMARY KEY,
                    name VARCHAR(128) NOT NULL
                )
            """);
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы Department : " + e.getMessage());
            throw e;
        }
    }


    private static void insertDepartments(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                INSERT INTO Department (id, name) VALUES
                (1, 'Department#1'),
                (2, 'Department#2'),
                (3, 'Department#3'),
                (4, 'Department#4'),
                (5, 'Department#5'),
                (6, 'Department#6')
            """);
        }
    }

    private static void insertPersons(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                INSERT INTO Person (id, name, age, active, department_id) VALUES
                (1, 'Person#1', 30, true, 1),
                (2, 'Person#2', 25, true, 1),
                (3, 'Person#3', 40, false, 2),
                (4, 'Person#4', 22, true, 2),
                (5, 'Person#5', 45, false, 3),
                (6, 'Person#6', 65, false, 4),
                (7, 'Person#7', 40, true, 5),
                (8, 'Person#8', 50, false, 6),
                (9, 'Person#9', 25, false, 6),
                (10, 'Person#10', 55, false, 4),
                (11, 'Person#11', 44, false, 4),
                (12, 'Person#12', 60, true, 3)
            """);
        }
    }


    /**
     * Пункт 4
     */
    private static Optional<String> getPersonDepartmentName(Connection connection, long personId) throws SQLException {
        // FIXME: Ваш код тут
        String query = """
            SELECT d.name
            FROM Person p
            JOIN Department d ON p.department_id = d.id
            WHERE p.id = ?
        """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, personId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getString("name"));
            }
        }
        return Optional.empty();
//        throw new UnsupportedOperationException();
    }

    /**
     * Пункт 5
     */
    private static Map<String, Optional<String>> getPersonDepartments(Connection connection) throws SQLException {
        // FIXME: Ваш код тут
        String query = """
            SELECT p.name AS person_name, d.name AS department_name
            FROM Person p
            JOIN Department d ON p.department_id = d.id
        """;
        Map<String, Optional<String>> personDepartmentMap = new HashMap<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                personDepartmentMap.put(resultSet.getString("person_name"),
                        Optional.ofNullable(resultSet.getString("department_name")));
            }
        }
        return personDepartmentMap;
//        throw new UnsupportedOperationException();
    }

    /**
     * Пункт 6
     */
    private static Map<String, List<String>> getDepartmentPersons(Connection connection) throws SQLException {
        // FIXME: Ваш код тут
        String query = """
            SELECT d.name AS department_name, p.name AS person_name
            FROM Department d
            JOIN Person p ON d.id = p.department_id
        """;
        Map<String, List<String>> departmentPersonMap = new HashMap<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String departmentName = resultSet.getString("department_name");
                String personName = resultSet.getString("person_name");
                departmentPersonMap
                        .computeIfAbsent(departmentName, k -> new ArrayList<>())
                        .add(personName);
            }
        }
        return departmentPersonMap;
//        throw new UnsupportedOperationException();
    }

    private static String formatPersonDepartments(Map<String, Optional<String>> map) {
        return map.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue().orElse("Not found"))
                .collect(Collectors.joining(",\n", "{\n", "\n}"));
    }

    private static String formatDepartmentPersons(Map<String, List<String>> map) {
        return map.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(",\n", "{\n", "\n}"));
    }

}
