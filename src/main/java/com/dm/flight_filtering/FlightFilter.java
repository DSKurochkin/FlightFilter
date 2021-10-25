package com.dm.flight_filtering;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.dm.flight_filtering.ConsoleUtil.*;

public class FlightFilter implements DynamicFilter<Flight> {

    private static final Map<String, Predicate<Flight>> filters;

    static {
        filters = new HashMap<>();
        filters.put("DepartureBeforeNow", flight -> flight.getSegments().stream()
                .allMatch(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now())));
        filters.put("ArrivalBeforeDeparture", flight -> flight.getSegments().stream()
                .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())));

        filters.put("MoreThenTwoHoursOnGround",
                flight -> (Duration.
                        between(flight.getSegments().get(0).getDepartureDate()
                                , flight.getSegments().get(flight.getSegments().size() - 1).getArrivalDate())
                        .toHours())
                        -
                        flight.getSegments().stream().mapToLong(s -> Duration.between(s.getDepartureDate(), s.getArrivalDate()).toHours()).sum()
                        <= 2);

    }

    @Override
    public List<Flight> filter(List<Flight> source, String... filterName) {
        return source.stream()
                .filter(getCompositeFilter(filterName))
                .collect(Collectors.toList());
    }

    private Predicate<Flight> getCompositeFilter(String... filterName) {
        Set<String> names = new HashSet<>(Arrays.asList(filterName));
        for (String name : names) {
            if (!filters.containsKey(name)) {
                throw new IllegalArgumentException(invalidFilterName);
            }
        }

        if (filterName.length == 1) {
            return filters.get(filterName[0]);
        }

        if (filterName.length == 0) {
            print(noFilters);
        }
        return filters.entrySet().stream()
                .filter(entry -> names.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .reduce(value -> true, Predicate::and);

    }


}
