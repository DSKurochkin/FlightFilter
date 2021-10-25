package com.dm.flight_filtering;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FlightFilterTest {
    private final List<Flight> source = FlightBuilder.createFlights();
    private final FlightFilter filter = new FlightFilter();
    private List<Flight> expected;

    @BeforeEach
    void initTestList() {
        expected = new ArrayList<>(source);
    }

    @Test
    void shouldFilterDepartureBeforeNow() {
        expected.remove(2);
        assertEquals(expected, filter.filter(source, "DepartureBeforeNow"));
    }

    @Test
    void shouldFilterArrivalBeforeDeparture() {
        expected.remove(3);
        assertEquals(expected, filter.filter(source, "ArrivalBeforeDeparture"));
    }

    @Test
    void shouldFilterTimeMoreThenTwoHoursOnGround() {
        expected.remove(4);
        expected.remove(4);
        assertEquals(expected, filter.filter(source, "MoreThenTwoHoursOnGround"));
    }

    @Test
    void shouldFilterDepartureBeforeNowAndArrivalBeforeDeparture() {
        expected.remove(2);
        expected.remove(2);
        assertEquals(expected, filter.filter(source, "DepartureBeforeNow", "ArrivalBeforeDeparture"));
    }

    @Test
    void shouldFilterByAllCriteria() {
        for (int i = 0; i < 4; i++) {
            expected.remove(2);
        }
        assertEquals(expected, filter.filter(source
                , "DepartureBeforeNow"
                , "ArrivalBeforeDeparture"
                , "MoreThenTwoHoursOnGround"));
    }

    @Test
    void shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> filter.filter(source, "NoExistFilterName"));
    }

}
