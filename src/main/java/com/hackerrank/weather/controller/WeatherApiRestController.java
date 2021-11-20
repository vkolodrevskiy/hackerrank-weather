package com.hackerrank.weather.controller;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.service.WeatherService;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
}
