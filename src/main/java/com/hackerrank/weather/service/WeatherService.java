package com.hackerrank.weather.service;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.WeatherRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
  private final WeatherRepository weatherRepository;

  public WeatherService(WeatherRepository weatherRepository) {
    this.weatherRepository = weatherRepository;
  }

  public Weather save(Weather weather) {
    return weatherRepository.save(weather);
  }

  public Optional<Weather> findById(Integer id) {
    return weatherRepository.findById(id);
  }
}
