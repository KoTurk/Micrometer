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

        Timer timerLeClerq = Timer.builder(timerName).tag(tag, driverLeClerqTag).register(Metrics.globalRegistry);
        Timer timerVerstappen = Timer.builder(timerName).tag(tag, driverVerstappenTag).register(Metrics.globalRegistry);

        timerLeClerq.record(Duration.ofSeconds(ThreadLocalRandom.current().nextLong(3,20)));
        timerVerstappen.record(Duration.ofSeconds(ThreadLocalRandom.current().nextLong(3,20)));

        Timer timer = ((Timer) Metrics.globalRegistry.getMeters().stream()
                    .filter(meter -> meter.getId().getTag(tag).equals(driverVerstappenTag))
                    .findFirst()
                    .orElse(Metrics.timer(timerName)));

        // TODO remove the fastest lap from the registry
        //  for a driver because crossing a white line
        //  use a method from Metrics.globalRegistry
        Metrics.globalRegistry.remove(timer);
    }
}
