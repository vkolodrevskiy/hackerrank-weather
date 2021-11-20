package com.hackerrank.weather.controller;

import com.hackerrank.weather.model.WeatherSortingType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WeatherSortingConverter implements Converter<String, WeatherSortingType> {

  @Override
  public WeatherSortingType convert(String source) {
    if (StringUtils.equalsIgnoreCase("date", source)) {
      return WeatherSortingType.ASC_DATE;
    } else if (StringUtils.equalsIgnoreCase("-date", source)) {
      return WeatherSortingType.DESC_DATE;
    } else {
      return WeatherSortingType.UNDEFINED;
    }
  }
}
