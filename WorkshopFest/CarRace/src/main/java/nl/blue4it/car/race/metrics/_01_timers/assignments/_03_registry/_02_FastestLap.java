package nl.blue4it.car.race.metrics._01_timers.assignments._03_registry;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class _02_FastestLap {

    public void remove() {
        String timerName = "assignment_1.3.2";
        String tag = "driver";
        String driverVerstappenTag = "verstappen";
        String driverLeClerqTag = "leclerq";

        // TODO create a timer with tag driver leclerq
        // TODO create a timer with tag driver verstappen
        Timer timerLeClerq = null;
        Timer timerVerstappen = null;

        timerLeClerq.record(Duration.ofSeconds(ThreadLocalRandom.current().nextLong(3,20)));
        timerVerstappen.record(Duration.ofSeconds(ThreadLocalRandom.current().nextLong(3,20)));

        // TODO get all the meters,
        //  with registry.getMeters().stream
        //  filter on tag driver Verstappen
        Timer timer = null;

        // TODO remove the fastest lap from the registry
        //  for a driver because crossing a white line
        //  use a method from Metrics.globalRegistry
    }
}
