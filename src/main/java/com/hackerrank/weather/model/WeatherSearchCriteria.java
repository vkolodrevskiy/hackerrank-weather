package com.hackerrank.weather.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class WeatherSearchCriteria {

  private final LocalDate date;
  private final Set<String> cities;
  private final WeatherSortingType sort;

  public static WeatherSearchCriteria of(LocalDate date, Set<String> city,
      WeatherSortingType sort) {
    Set<String> loweredCities = null;
    if (city != null) {
      loweredCities = city.stream()
          .map(String::toUpperCase)
          .collect(Collectors.toSet());
    }

    return new WeatherSearchCriteria(date, loweredCities, sort);
  }

  private WeatherSearchCriteria(LocalDate date, Set<String> city, WeatherSortingType sort) {
    this.date = date;
    this.cities = city;
    this.sort = sort;
  }

  public LocalDate getDate() {
    return date;
  }

  public Set<String> getCities() {
    return cities;
  }

  public WeatherSortingType getSort() {
    return sort;
  }

  public boolean isEmpty() {
    return date == null && cities == null && sort == WeatherSortingType.UNDEFINED;
  }
}
