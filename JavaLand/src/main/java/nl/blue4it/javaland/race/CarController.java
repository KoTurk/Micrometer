package nl.blue4it.javaland.race;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.Histogram;
import nl.blue4it.javaland.race.domain.FormulaOneCar;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/car/race")
public class CarController {
    FormulaOneCar car;
    PrometheusMeterRegistry prometheusMeterRegistry;

    public CarController(FormulaOneCar car, PrometheusMeterRegistry prometheusMeterRegistry) {
        this.car = car;
        this.prometheusMeterRegistry = prometheusMeterRegistry;
    }

    @RequestMapping
    @Timed(value = "car.race", longTask = true)
    public String race() throws InterruptedException {
        car.driving();
        return "race";
    }

    @RequestMapping("/pitstop")
    @Timed(value = "car.race.pitstop")
    public String pitstop() {
        car.refuel();
        return "made a pitstop";
    }

    @RequestMapping("/pitstops")
    public String pitstops() throws InterruptedException {
        long start = System.currentTimeMillis();
        Timer timer = Timer.builder("car.race.histogram")
                .publishPercentiles(0.5, 0.95) // how many between these values, so between 0.5 and 0.95 seconds
                .publishPercentileHistogram()
                .sla(Duration.ofSeconds(8))
                .register(prometheusMeterRegistry);

        car.refuelAndWait();
        long stop = System.currentTimeMillis();
        long total = ( stop - start) / 100;

        timer.record(total, TimeUnit.SECONDS);

        return "did a comparison";
    }

    @RequestMapping("/lap")
    @Counted
    public String addLap() {
        Metrics.counter("car.race.lap2").increment();
        return "added a lap";
    }

    @RequestMapping("/tires")
    public String tires() {
        Metrics.gauge("car.race.tires", car.getTireWear());
        return "getting the tire wear";
    }
}
