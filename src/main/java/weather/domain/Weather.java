package weather.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonProperty("name")
    private String cityName;
    @JsonProperty("main")
    private HashMap<String,String> temp_min;
    @JsonProperty("main.")
    private String temp_max;

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return "Weather{" +
                "cityName='" + cityName + '\'' +
                ", Date='" + dateFormat.format(date) + '\'' +
                ", temp_min=" + temp_min.get("temp_min") +
                ", temp_max=" + temp_max +
                '}';
    }
}
