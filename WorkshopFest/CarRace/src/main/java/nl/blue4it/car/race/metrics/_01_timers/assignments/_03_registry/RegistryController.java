package nl.blue4it.car.race.metrics._01_timers.assignments._03_registry;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/car/race")
@RequiredArgsConstructor
public class RegistryController {
    private final _02_FastestLap fastestLap;

    @GetMapping("fastestlap/remove")
    public String remove() {
        fastestLap.remove();
        return "Removed the timer";
    }

}
