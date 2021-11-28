package nl.blue4it.ownyoursoftware.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Metrics;
import nl.blue4it.ownyoursoftware.domain.FormulaOneCar;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car/race")
public class CarController {
    FormulaOneCar car;

    public CarController(FormulaOneCar car) {
        this.car = car;
    }

    @GetMapping("pitstop")
   // @Timed("car.pitstop")
    public String doPitstop() {
        Metrics.timer("car.pitstop2").record(car.refuel());
        return "Refueled in " +  car.refuel() + " seconds";
    }

    @GetMapping()
    @Timed(value = "car.race", longTask = true)
    public String race() throws InterruptedException {
        car.driving();
        return "race is over";
    }

    @GetMapping("/lap")
    //@Counted
    public String addLap() {
        Metrics.counter("car.race.laps").increment();
        return "added a lap";
    }

    @GetMapping("/tires")
    public String tires() {
        final int tireWear = car.getTireWear();
        Metrics.gauge("car.race.tires.left", car.getTireWear());

        return "The lost " + tireWear + "%";
    }

    @GetMapping("/crash")
    public ResponseEntity<Object> crash() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
