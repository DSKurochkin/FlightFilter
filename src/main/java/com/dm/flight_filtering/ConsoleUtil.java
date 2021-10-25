package com.dm.flight_filtering;

import java.util.List;

public class ConsoleUtil {
    public final static String invalidFilterName = "Invalid filter name entered";
    public final static String noFilters = "No filters have been added";


    public static void print(String s) {
        System.out.println(s);
    }

    public static void printResWithHeading(String heading, List<Flight> res) {
        print(heading);
        print("-".repeat(107));
        res.forEach(System.out::println);
        print("\n");
    }


}
