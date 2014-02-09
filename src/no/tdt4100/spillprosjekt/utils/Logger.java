package no.tdt4100.spillprosjekt.utils;

/**
 * Created by eiriksylliaas on 08.02.14.
 */
public class Logger {

    public static void log(Exception e) {
        System.out.print("Program Error\n" + e.getMessage() + "\n" + e.getStackTrace().toString());
    }

}
