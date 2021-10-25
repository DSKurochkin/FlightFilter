package com.dm.flight_filtering;

import java.util.List;

public interface DynamicFilter<T> {
    List<T> filter(List<T> source, String... filterName);

}
