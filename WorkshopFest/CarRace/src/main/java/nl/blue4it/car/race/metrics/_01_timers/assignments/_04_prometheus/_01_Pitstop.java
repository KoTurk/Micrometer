package nl.blue4it.car.race.metrics._01_timers.assignments._04_prometheus;

import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Component("04Pitstop")
@RequiredArgsConstructor
public class _01_Pitstop {
    private final PrometheusMeterRegistry prometheusMeterRegistry;

    public void sla() {
        // TODO create a Timer
        //  sla of 8 seconds
        //  minimum expected of 4 seconds
        //  maximum expected 7 seconds
        //  register it to prometheus
        //  record the randomSeconds with a unit of seconds
        Timer.builder("assignment_1.4")
            .sla(Duration.ofSeconds(6))
            .minimumExpectedValue(Duration.ofSeconds(3))
            .maximumExpectedValue(Duration.ofSeconds(10))
            .register(prometheusMeterRegistry)
            .record(ThreadLocalRandom.current().nextLong(3,20), TimeUnit.SECONDS);

        // TODO call endpoint http://localhost:8080/car/race/pitstop/sla
        //  and check prometheus endpoint to see that there is a different buck for the SLA time.
        // http://localhost:8080/actuator/prometheus
    }
}
