package com.mytests.course.ee.jdbc;

import java.util.logging.Logger;

/**
 *
 */
public class Car {
    public static final Logger log = null;

    private String carName;

    public Car(String carName) {
        this.carName = carName;
    }

    public static String doSomething() {
        System.out.println("Print something");
        return "";
    }

    public void tune(final String anyText) {
//        anyText = "bubo";
        System.out.println(anyText);
    }

    private void stop(){
        System.out.println("The car is stopped");
    }

    public final String doSound(int soundNumber) {
        String sound;
        if (soundNumber == 1) {
            sound = "Bi - bi";
            return sound;
        }
        if (soundNumber == 2) {
            sound = "Tu - tu";
            return sound;
        } else {
            sound = "...";
            return sound;
        }
    }
}
