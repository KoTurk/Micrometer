package nl.blue4it.car.race.metrics._01_timers.assignments._02_tags;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/car/race/pitstop")
@RequiredArgsConstructor
public class _01_TagsController {
    private final _02_Pitstop pitstop;

    // TODO create timer annotation with tags:
    //  tag id driver, value verstappen
    //  tag id changed, value tires
    //  tag id changed, value front
    @GetMapping("redbull")
    public String redBullPitstop() {
        return "Redbull did a pitstop in " + pitstop.pitstopRedBull().toSeconds() + " seconds";
    }

    @GetMapping("ferrari")
    public String ferrariPitstop() {
        return "Ferrari did a pitstop in " + pitstop.pitstopFerrari().toSeconds() + " seconds";
    }

    @GetMapping("crash")
    public void crash() {
        pitstop.crash();
    }

}
