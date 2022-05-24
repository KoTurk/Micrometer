package nl.blue4it.car.race.metrics._01_timers.assignments._02_tags;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// https://github.com/firedome/dynamic-actuator-metrics
// https://dzone.com/articles/spring-boot-metrics-with-dynamic-tag-values
@Component
@NoArgsConstructor
public class _03_TaggedTagTimer {
    public String name;
    public String tagName;
    private final Map<String, Timer> timers = new HashMap<>();

    public _03_TaggedTagTimer(String name, String tagName) {
        this.name = name;
        this.tagName = tagName;
    }
    public Timer getTimer(String tagValue){
       Timer timer = timers.get(name);

        if(timer == null) {
            timer = null; // TODO create timer here
            timers.put(tagValue, timer);
        }
        return timer;
    }
}

