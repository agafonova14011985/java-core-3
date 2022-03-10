package ru.geekbrains.jc3_feb.l7_reflection.home_work;



public class ClassForTesting {

    @Test(priority = 5)
    public void testMethod1() {
        System.out.println("Приоритет метода - 5");
    }

    @Test(priority = 1)
    public void testMethod2() {
        System.out.println("Приоритет метода -  1");
    }

    @Test(priority = 10)
    public void testMethod3() {
        System.out.println("Приоритет метода -  10");
    }

    @Test(priority = 7)
    private void testMethod4() {
        System.out.println("Приоритет метода - 7 ");
    }

    @Test
    public void testMethod5() {
        System.out.println("Приоритет по умолчанию");
    }

    @AfterSuite
    public void afterMethod() {
        System.out.println("@AfterSuite метод");
    }

    @BeforeSuite
    public void beforeSuiteMethod() {
        System.out.println("@BeforeSuite метод");
    }


}