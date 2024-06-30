## Java Junior (семинары)

### Урок 3. Сериализация

### Описание
- Создание класса с приватными полями, который реализует интерфейс Serializable.
- Запись объекта этого класса в файл с использованием ObjectOutputStream.
- Чтение объекта из файла с использованием ObjectInputStream и восстановление его состояния.
- Использование Java I/O для чтения и записи данных в файл.

### Домашнее задание 

```
public class Homework {

  /**
   * С помощью JDBC, выполнить следующие пункты:
   * 1. Создать таблицу Person (скопировать код с семинара)
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

  /**
   * Пункт 4
   */
  private static String getPersonDepartmentName(Connection connection, long personId) throws SQLException {
    // FIXME: Ваш код тут
    throw new UnsupportedOperationException();
  }

  /**
   * Пункт 5
   */
  private static Map<String, String> getPersonDepartments(Connection connection) throws SQLException {
    // FIXME: Ваш код тут
    throw new UnsupportedOperationException();
  }

  /**
   * Пункт 6
   */
  private static Map<String, List<String>> getDepartmentPersons(Connection connection) throws SQLException {
    // FIXME: Ваш код тут
    throw new UnsupportedOperationException();
  }

}
```