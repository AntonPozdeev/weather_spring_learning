package weather.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import weather.domain.Weather;
import weather.domain.WeatherRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class WeatherController {

    @Autowired
    WeatherRepository weatherRepository;

    @RequestMapping("/weather")
    public HttpEntity<Weather> getWeather(
            @RequestParam(value = "city", required = false, defaultValue = "Moscow,ru") String cityName) {

        Weather weather = null;
        try {
//            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=" + cityName
//                    + "&appid=2b3bbe25306f0838dbaaf2ba2b350ce8&units=metric");
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + cityName
                    + "&appid=2b3bbe25306f0838dbaaf2ba2b350ce8&units=metric");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;

            System.out.println("Output from Server .... \n");
            StringBuffer sb = new StringBuffer();
            while ((output = br.readLine()) != null) {
                sb.append(output);
                System.out.println(output);
            }
            System.out.println(sb.toString());
            ObjectMapper objectMapper = new ObjectMapper();
            weather = objectMapper.readValue(sb.toString(), Weather.class);
            System.out.println(weather);
            weatherRepository.save(weather);
            conn.disconnect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Weather w : weatherRepository.findAll()) {
            System.out.println(w.toString());
            }
        System.out.println("stop");

        return new ResponseEntity<Weather>(weather, HttpStatus.OK);
    }
}
