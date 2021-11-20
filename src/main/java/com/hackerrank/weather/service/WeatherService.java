package com.hackerrank.weather.service;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.model.WeatherSearchCriteria;
import com.hackerrank.weather.repository.WeatherRepository;
import com.hackerrank.weather.repository.WeatherRepositoryFilter;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

  private final WeatherRepository weatherRepository;
  private final WeatherRepositoryFilter weatherRepositoryFilter;

  public WeatherService(WeatherRepository weatherRepository,
      WeatherRepositoryFilter weatherRepositoryFilter) {
    this.weatherRepository = weatherRepository;
    this.weatherRepositoryFilter = weatherRepositoryFilter;
  }

  public Weather save(Weather weather) {
    return weatherRepository.save(weather);
  }

  public Optional<Weather> findById(Integer id) {
    return weatherRepository.findById(id);
  }

  public List<Weather> filterWeather(WeatherSearchCriteria searchCriteria) {
    final List<Weather> weatherList;
    if (searchCriteria.isEmpty()) {
      return weatherList = weatherRepository.findAll();
    } else {
      return weatherList = weatherRepositoryFilter.filterWeather(searchCriteria);
    }
  }
}
