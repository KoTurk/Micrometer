package nl.blue4it.paymentengine.rest.domain;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import java.util.HashMap;
import java.util.Map;

// https://github.com/firedome/dynamic-actuator-metrics
// https://dzone.com/articles/spring-boot-metrics-with-dynamic-tag-values
public class TaggedTimer {
    private String name;
    private String tagName;
    private MeterRegistry registry;
    private Map<String, Timer> timers = new HashMap<>();

    public TaggedTimer(String name, String tagName, MeterRegistry registry) {
        this.name = name;
        this.tagName = tagName;
        this.registry = registry;
    }

    public Timer getTimer(String tagValue){
        Timer timer = timers.get(tagValue);
        if(timer == null) {
            timer = Timer.builder(name).tags(tagName, tagValue).register(registry);
            timers.put(tagValue, timer);
        }
        return timer;
    }
}
