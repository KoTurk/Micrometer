package nl.blue4it.car.race.metrics._03_gauges;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import nl.blue4it.car.race.metrics._03_gauges.assignments._01_WeatherAPI;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/car/race/strategy")
@RequiredArgsConstructor
public class StrategyController {
    private final _01_WeatherAPI weatherAPI;

    @GetMapping("/weather")
    public String weather() {
        return "The weather during the race is changed to " + weatherAPI.get();
    }
}

