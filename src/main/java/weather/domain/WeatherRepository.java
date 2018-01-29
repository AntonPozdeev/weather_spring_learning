package weather.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeatherRepository extends CrudRepository<Weather, Long> {

    List<Weather> findByCityName(String cityName);
}
