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
        String timerName = "assignment_1.3.1";
        Metrics.timer(timerName).record(Duration.ofSeconds(3));
        Metrics.timer(timerName).record(Duration.ofSeconds(6));

        // TODO get the max pitstop time
        // with max from the Timer

//        return ((Timer) Metrics.globalRegistry.getMeters()
//                .stream()
//                    .filter(meter -> meter.getId().getName().equals(timerName))
//                    .findFirst()
//                    .get())

        return 0.0;
    }
}
