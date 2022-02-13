package ru.geekbrains.jc3_feb.l1_generics;

//коробка с числами из массива чисел extends Number - условие только числа, ограничение по классу или классам или интерфейсу (сам)
// теесть или ограничение по интерфейсу, то они должны еще и реализовывать эти интерфейсы
public class BoxWithNumbers<N extends Number> {
    private N[] numbers;

    public BoxWithNumbers(N... numbers) { //(N... numbers)- вараркс, можем передовать один или несколько элементов, массив...
        this.numbers = numbers;
    }

    //    public boolean equalsByAvg(BoxWithNumbers<? extends Number> another) {
    //сравнение коробок где <?> любое
    public boolean equalsByAvg(BoxWithNumbers<?> another) {
        return Math.abs(avg() - another.avg()) < 0.00001;
    }

    //метод считающий среднее
    public double avg() {
        return sum() / numbers.length;//находим среднее
    }

    public double sum() {
        var sum = 0.0;//инициализация
        for (N number : numbers) {//проходимся по массиву
            sum += number.doubleValue();
        }
        return sum;
    }

    public N[] getNumbers() {
        return numbers;
    }

    public void setNumbers(N[] numbers) {
        this.numbers = numbers;
    }
}