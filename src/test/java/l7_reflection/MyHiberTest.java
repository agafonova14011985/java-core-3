package l7_reflection;

import ru.geekbrains.jc3_feb.l7_reflection.Cat;
import ru.geekbrains.jc3_feb.l7_reflection.Employee;
import ru.geekbrains.jc3_feb.l7_reflection.LittleHiber;
//import ru.geekbrains.jc3_feb.l7_reflection.little_hiber.Cat;
//import ru.geekbrains.jc3_feb.l7_reflection.little_hiber.Employee;
//import ru.geekbrains.jc3_feb.l7_reflection.little_hiber.LittleHiber;

public class MyHiberTest {
    public static void main(String[] args) {
        LittleHiber.createTable(Cat.class);
        LittleHiber.createTable(Employee.class);

        Cat[] cats = {
                new Cat(0, "C1", "B1"),
                new Cat(0, "C2", "B2"),
                new Cat(0, "C3", "B3"),
                new Cat(0, "C4", "B4"),
                new Cat(0, "C5", "B5"),
        };

        Employee[] employees = {
                new Employee(1, "dff", 10, 2324, "sdgf", "sdvsfdv", "szfdgsfd"),
                new Employee(2, "dff", 10, 2324, "sdgf", "sdvsfdv", "szfdgsfd")
        };

        for (Cat cat : cats) {
            LittleHiber.insertObject(cat);
        }

        for (Employee employee : employees) {
            LittleHiber.insertObject(employee);
        }
    }
}