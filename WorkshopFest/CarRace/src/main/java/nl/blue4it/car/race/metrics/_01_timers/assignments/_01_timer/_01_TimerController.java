package nl.blue4it.car.race.metrics._01_timers.assignments._01_timer;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/car/race/pitstop")
@RequiredArgsConstructor
public class _01_TimerController {
    private final _02_Pitstop pitstop;

    @GetMapping("tires")
    @Timed(value = "assignment_1.1.1")
    public String tires() {
        return "We did a pitstop in " + pitstop.changeTires().toSeconds() + " seconds";
    }

    @GetMapping("front")
    public String frontAndTires() {
        return "We did a pitstop in " + pitstop.changeFrontAndTires().toSeconds() + " seconds";
    }

    @GetMapping("penalty")
    public String penalty() {
        return "We stopped because of a penalty, " + pitstop.penalty().toSeconds() + " seconds";
    }

    @GetMapping("wrong")
    @Timed(value = "assignment_1.1.1", longTask = true)
    public String pitstop() throws InterruptedException {
        pitstop.somethingsWrong();
        return "We did a pitstop";
    }
}
