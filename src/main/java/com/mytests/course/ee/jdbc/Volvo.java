package com.mytests.course.ee.jdbc;


import java.util.logging.Logger;

/**
 *
 */
public class Volvo extends Car {
    public Volvo(String carName) {
        super(carName);
//        Car.log = Logger.getAnonymousLogger();
    }

    @Override
    public void tune(String anyText) {
        anyText = "Bu bo";
        super.tune(anyText);
    }

}
