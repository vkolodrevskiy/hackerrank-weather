package com.hackerrank.weather.service;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

  @Autowired
  private WeatherRepository weatherRepository;

  public Weather save(Weather weather) {
    return weatherRepository.save(weather);
  }




}
