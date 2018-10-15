package com.company;

import java.awt.*;

import static com.company.ColorsClass.ANSI_PURPLE;

public class Main {
    private static void print(String string){
        System.out.println(string);
    }

    public static void main(String[] args) throws InterruptedException {
        PoliceClass policeClass = new PoliceClass();
        policeClass.run();
        Thread.State state = Thread.currentThread().getState();
        if (state!=Thread.State.NEW&&state!=Thread.State.TERMINATED){
            print("Thread main: " + state);
        }
    }
}