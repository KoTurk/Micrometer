package nl.blue4it.car.race.metrics._02_counters;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import nl.blue4it.car.race.metrics._02_counters.assignments._01_Overtakes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/car/race/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final _01_Overtakes overtakes;

    @GetMapping("/overtake")
    public String getOvertakes() {
        return "Ferrari overtaked " + overtakes.count() + " times";
    }

}
