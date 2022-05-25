package nl.blue4it.car.race.metrics._01_timers.assignments._01_timer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// https://github.com/firedome/dynamic-actuator-metrics
// https://dzone.com/articles/spring-boot-metrics-with-dynamic-tag-values
@Component
@RequiredArgsConstructor
public class _03_TaggedTimer {
    private final Map<String, Timer> timers = new HashMap<>();

    public Timer getTimer(String name){
        Timer timer = timers.get(name);

        if(timer == null) {
            timer = Timer.builder("assignment_1.1.5")
                    .register(Metrics.globalRegistry);
            timers.put(name, timer);
        }
        return timer;
    }
}

