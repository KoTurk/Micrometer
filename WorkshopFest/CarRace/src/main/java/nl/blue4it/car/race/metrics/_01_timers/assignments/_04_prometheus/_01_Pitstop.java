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

        // TODO call endpoint and check prometheus endpoint to see that there is a different buck for the SLA time.
    }
}
