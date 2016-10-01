package com.objects;

/**
 * Created by admin on 9/30/16.
 */
public class Log {

    public static boolean debugging = true;

    static void print(String toPrint) {
        if (debugging) {
            System.out.println(toPrint);
        }

        //If not debugging, ignore.
    }
}
