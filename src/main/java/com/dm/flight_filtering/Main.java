package com.dm.flight_filtering;

import java.util.List;

import static com.dm.flight_filtering.ConsoleUtil.printResWithHeading;

public class Main {
    public static void main(String[] args) {
        List<Flight> source = FlightBuilder.createFlights();
        FlightFilter filter = new FlightFilter();

        printResWithHeading("Primary list of flights", source);
        printResWithHeading("Filter flights departing in the past"
                , filter.filter(source, "DepartureBeforeNow"));
        printResWithHeading("Filter flights that arrives before departs "
                , filter.filter(source, "ArrivalBeforeDeparture"));
        printResWithHeading("Filter flights with more than two hours ground time"
                , filter.filter(source, "MoreThenTwoHoursOnGround"));

    }
}
