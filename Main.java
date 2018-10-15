package com.company;

import java.awt.*;

import static com.company.Colors.ANSI_PURPLE;

public class Main extends Thread{

    private static void print(String string) {
        System.out.println(string);
    }
    public static void main(String[] args) {
//        print(Thread.getAllStackTraces().toString());

        PoliceClass policeClass = new PoliceClass();
        policeClass.run();

        Thread.State currState = Thread.currentThread().getState();
        if (currState != Thread.State.NEW && currState != Thread.State.TERMINATED){
            print("ThreaD Main: " + currState);
        }

//        System.out.println(ANSI_PURPLE + "ERROR MESSAGE IN RED");
    }
}
