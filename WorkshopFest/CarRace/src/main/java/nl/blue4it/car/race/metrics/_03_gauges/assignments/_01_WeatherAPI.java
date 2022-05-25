package nl.blue4it.car.race.metrics._03_gauges.assignments;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Metrics;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class _01_WeatherAPI {

    public String get() {
        String weather = randomWeather();

        // TODO create a Gauge, with value randomHardness
        //  and tag condition and put weather in it
        //  and register it
        Gauge.builder("assignment_3.1", this::randomHardness)
                .tags("condition", randomWeather())
                .strongReference(true)
                .register(Metrics.globalRegistry);

        return weather;
    }

    private String randomWeather() {
        String[] weatherConditions = {"SUNNY", "RAIN", "CLOUDY", "SNOW"};
        Random random = new Random();
        int randomNumber = random.nextInt(weatherConditions.length);

        return weatherConditions[randomNumber];
    }
    private int randomHardness(){
        return ThreadLocalRandom.current().nextInt(0,100);
    }
}
