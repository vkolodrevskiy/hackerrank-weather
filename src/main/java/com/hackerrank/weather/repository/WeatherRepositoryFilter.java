package com.hackerrank.weather.repository;


import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.model.WeatherSearchCriteria;
import com.hackerrank.weather.model.WeatherSortingType;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class WeatherRepositoryFilter {

  @PersistenceContext
  private EntityManager em;

  public List<Weather> filterWeather(WeatherSearchCriteria searchCriteria) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Weather> criteriaQuery = cb.createQuery(Weather.class);
    Root<Weather> weather = criteriaQuery.from(Weather.class);

    criteriaQuery.select(weather);

    List<Predicate> predicates = getFilterPredicates(searchCriteria, cb, weather);
    if (!predicates.isEmpty()) {
      criteriaQuery.where(predicates.toArray(new Predicate[]{}));
    }

    if (searchCriteria.getSort() != WeatherSortingType.UNDEFINED) {
      final Expression<String> expression = cb.trim(weather.get("date"));

      List<Order> orderList = new ArrayList<>();

      if (searchCriteria.getSort() == WeatherSortingType.DESC_DATE) {
        orderList.add(cb.desc(expression));
      } else if (searchCriteria.getSort() == WeatherSortingType.ASC_DATE) {
        orderList.add(cb.asc(expression));
      }
      orderList.add(cb.asc(weather.get("id")));
      criteriaQuery.orderBy(orderList);

    }

    return em.createQuery(criteriaQuery).getResultList();
  }

  private List<Predicate> getFilterPredicates(WeatherSearchCriteria searchCriteria,
      CriteriaBuilder builder, Root<Weather> weather) {
    Predicate dateCondition = null;
    Predicate citiesCondition = null;

    if (searchCriteria.getDate() != null) {
      dateCondition = builder.lessThanOrEqualTo(weather.get("date"),
          Date.from(searchCriteria.getDate().atStartOfDay(
              ZoneId.systemDefault()).toInstant()));
    }

    if (searchCriteria.getCities() != null) {
      citiesCondition = builder.upper(weather.get("city")).in(searchCriteria.getCities());
    }

    return Stream.of(dateCondition, citiesCondition).filter(Objects::nonNull).collect(
        Collectors.toList());
  }
}
