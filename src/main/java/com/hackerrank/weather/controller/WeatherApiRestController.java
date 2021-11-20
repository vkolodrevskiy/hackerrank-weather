package com.hackerrank.weather.controller;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.service.WeatherService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class WeatherApiRestController {

  @Autowired
  private WeatherService weatherService;

  @PostMapping("/weather")
  public ResponseEntity<Weather> createWeather(@Valid @RequestBody Weather weather) {
      Weather createdWeather = weatherService.save(weather);
      return new ResponseEntity<>(createdWeather, HttpStatus.CREATED);
  }
}
