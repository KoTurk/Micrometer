package nl.blue4it.car.race;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import nl.blue4it.car.race.domain.FormulaOneCar;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("car/race")
public class CarController {
    FormulaOneCar car;
    PrometheusMeterRegistry registry;

    public CarController(FormulaOneCar car, PrometheusMeterRegistry registry) {
        this.car = car;
        this.registry = registry;
    }

    @RequestMapping
    @Timed(value = "car.race.longtasktimer", longTask = true)
    public String race() throws InterruptedException {
        car.driving();
        return "race";
    }

    @RequestMapping("/pitstop")
    @Timed("car.race.timer")
    public String pitstop() throws InterruptedException {
        car.refuelAndWait();
//        Metrics.timer("car.race.timer2").record(car.refuel());
//        Timer.builder("car.race.pitstop.timer3").tags("MaxVerstappen").register(registry);
        return "race";
    }

    @RequestMapping("/pitstops")
    public void getHistogram() throws InterruptedException {
        long start = System.currentTimeMillis();
        Timer timer = Timer.builder("my.histogram.timer")
                .publishPercentiles(0.5, 0.95) // how many between these values, so between 0.5 and 0.95 seconds
                .publishPercentileHistogram()
                .sla(Duration.ofMillis(100))
                .minimumExpectedValue(Duration.ofMillis(1))
                .maximumExpectedValue(Duration.ofSeconds(10))
                .register(registry);
        car.refuelAndWait();
        long stop = System.currentTimeMillis();

        timer.record(stop - start, TimeUnit.MILLISECONDS);
    }

    @RequestMapping("addlap")
    public String addLap() {
        Metrics.counter("car.race.counter").increment();
        return "adding a lap";
    }

    @RequestMapping("tires")
    public String getTires() {
        Metrics.gauge("car.race.gauge", car.getTireWear());
        return "adding a lap";
    }
}
