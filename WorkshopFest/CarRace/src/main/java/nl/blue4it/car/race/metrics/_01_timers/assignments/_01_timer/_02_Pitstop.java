package nl.blue4it.car.race.metrics._01_timers.assignments._01_timer;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import nl.blue4it.car.race.metrics._01_timers.assignments._01_timer._03_TaggedTimer;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component("01Pitstop")
@RequiredArgsConstructor
public class _02_Pitstop {
    private final _03_TaggedTimer taggedTimer;

    public Duration changeTires() {
        return Duration.ofSeconds(4);
    }

    public Duration changeFrontAndTires() {
        Duration frontSeconds = Duration.ofSeconds(6);
        Duration tiresSeconds = changeTires();

        // TODO create timer from Metrics class,
        //  give it a name, and only measure the front seconds

        return frontSeconds.plus(tiresSeconds);
    }

    public Duration penalty() {
        Duration penalty = Duration.ofSeconds(10);

        // TODO create timer from Timer interface,
        //  give it a name, and only record the penalty seconds

        // or create timer in taggedTimer (less memory needed)
        taggedTimer.getTimer("penalty").record(penalty);

        return penalty;
    }

    public void somethingsWrong() throws InterruptedException {
        Thread.sleep(10000L);
    }
}
