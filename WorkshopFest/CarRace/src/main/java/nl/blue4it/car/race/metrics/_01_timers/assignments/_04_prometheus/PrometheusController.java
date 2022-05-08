package nl.blue4it.car.race.metrics._01_timers.assignments._04_prometheus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/car/race/pitstop")
public class PrometheusController {
    _01_Pitstop pitstop;

    public PrometheusController(_01_Pitstop pitstop) {
        this.pitstop = pitstop;
    }

    @GetMapping("sla")
    public String sla() {
        pitstop.sla();
        return "Created a timer that tracks if the pitstops are ok";
    }



}
