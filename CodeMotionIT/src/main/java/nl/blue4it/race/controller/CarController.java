package nl.blue4it.race.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import nl.blue4it.race.domain.FormulaOneCar;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/car/race")
public class CarController {
    FormulaOneCar car;
    PrometheusMeterRegistry registry;

    public CarController(FormulaOneCar car, PrometheusMeterRegistry registry) {
        this.car = car;
        this.registry = registry;
    }

    @RequestMapping("")
    @Timed(value = "car.race.longtasktimer", longTask = true)
    public String race() throws InterruptedException {
        car.driving();
        return "we're racing";
    }

    @RequestMapping("/pitstop")
    @Timed(value = "car.race.timer")
    public String pitstop() throws InterruptedException {
//        Metrics.timer("car.race.timer").record(Duration.ofSeconds(10));

        car.refuel();
        return "refuelling";
    }

    @RequestMapping("/pitstops")
    public void getHistogram() throws InterruptedException {
        long start = System.currentTimeMillis();
        Timer timer = Timer.builder("car.race.histogram")
                .publishPercentiles(0.5, 0.95) // how many between these values, so between 0.5 and 0.95 seconds
                .publishPercentileHistogram()
                .sla(Duration.ofSeconds(8))
                .minimumExpectedValue(Duration.ofSeconds(5))
                .maximumExpectedValue(Duration.ofSeconds(10))
                .register(registry);

        car.refuelAndWait();

        long stop = System.currentTimeMillis();
        long total = ( stop - start) / 100;
        System.out.println("---------->" + total);
        timer.record(total, TimeUnit.SECONDS);
    }

    @RequestMapping("/laps")
    @Counted
    public String addOneLap() {
        Metrics.counter("car.race.laps.counter").increment();
        return "Added a lap";
    }

    @RequestMapping("/tires")
    public String getTires() {
        Metrics.gauge("car.race.tires.gauge", car.getTireWear());
        return "how much tires are there left";
    }
}
