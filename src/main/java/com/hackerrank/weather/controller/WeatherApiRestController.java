package com.hackerrank.weather.controller;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.model.WeatherSearchCriteria;
import com.hackerrank.weather.model.WeatherSortingType;
import com.hackerrank.weather.service.WeatherService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class WeatherApiRestController {

  private final WeatherService weatherService;

  public WeatherApiRestController(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  @PostMapping("/weather")
  public ResponseEntity<Weather> addWeather(@RequestBody @Valid Weather weather) {
    Weather createdWeather = weatherService.save(weather);
    return new ResponseEntity<>(createdWeather, HttpStatus.CREATED);
  }

  @GetMapping(value = "/weather/{id}")
  public ResponseEntity<Weather> getWeatherById(@PathVariable @NotNull Integer id) {
    Optional<Weather> weatherOp = weatherService.findById(id);
    return weatherOp.map(weather -> ResponseEntity.status(HttpStatus.OK).body(weather))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @GetMapping(value = "/weather")
  public List<Weather> filterWeather(
      @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
      @RequestParam(value = "city", required = false) Set<String> city,
      @RequestParam(value = "sort", required = false) WeatherSortingType sort) {
    WeatherSearchCriteria searchCriteria = WeatherSearchCriteria.of(date, city, sort);
    return weatherService.filterWeather(searchCriteria);
  }
}
