package nl.blue4it.car.race.metrics._01_timers.assignments._03_registry;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Component("03Pitstop")
public class _01_Pitstop {
    public double compare() {
        // TODO create a timer and give a Duration of 3 and 6 seconds, these are the pitstops we made
        String timerName = "assignment_1.3.1";

        // TODO get the max pitstop time and return
        // with registry.getMeters().stream
        // filter on name
        return 0.0;
    }
}
