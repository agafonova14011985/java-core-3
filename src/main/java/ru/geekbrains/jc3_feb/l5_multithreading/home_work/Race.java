package ru.geekbrains.jc3_feb.l5_multithreading.home_work;

import java.util.ArrayList;
import java.util.Arrays;

class Race {
    private ArrayList<Stage> stages;
    ArrayList<Stage> getStages() { return stages; }
    private int finishCount;


    Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }

    public synchronized void finish(Car car) {
        if (finishCount++ == 0) {
            System.out.println("\nПобедитель: " + car.getName()+"\n" );
        }if
            (finishCount != 0) {
            System.out.println("Закончил соревнования " + car.getName() );}

    }
}