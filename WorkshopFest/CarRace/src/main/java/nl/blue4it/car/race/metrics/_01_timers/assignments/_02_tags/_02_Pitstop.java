package nl.blue4it.car.race.metrics._01_timers.assignments._02_tags;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Duration;

@Component("02Pitstop")
@RequiredArgsConstructor
public class _02_Pitstop {

    public Duration pitstopRedBull() {
        return Duration.ofSeconds(6);
    }

    public Duration pitstopFerrari() {
        Duration pitstop = Duration.ofSeconds(3);

        // TODO Create Timer with builder, tag driver and LeClerq as value

        _03_TaggedTagTimer taggedTimer = new _03_TaggedTagTimer("assignment_1.2.3", "driver");
        taggedTimer.getTimer("leclerq").record(pitstop);

        return pitstop;
    }

    @GetMapping("/crash")
    public ResponseEntity<Object> crash() {
        // TODO Throw an exception instead of null and find status in actuator
        //http://localhost:8080/actuator/metrics/http.server.requests?tag=uri:/car/race/crash
        return null;
    }
}
