package nl.blue4it.car.race.metrics._01_timers.assignments._01_timer;

import io.micrometer.core.instrument.MeterRegistry;
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
    private final MeterRegistry registry;
    private final Map<String, Timer> timers = new HashMap<>();


    public Timer getTimer(String name){
        // TODO get the timer from the hashmap
        Timer timer = null;

        if(timer == null) {
            // TODO create timer with builder
            timer = null;
            timers.put(name, timer);
        }
        return timer;
    }
}

