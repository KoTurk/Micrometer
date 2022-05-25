package nl.blue4it.car.race.metrics._02_counters.assignments;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.stereotype.Component;

@Component
public class _01_Overtakes {

    public double count() {

        // TODO Create a counter and add one up
        Counter counter = Metrics.counter("overtakes");
        counter.increment();

        return counter.count();
    }
}
