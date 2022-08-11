package nl.confluent.construction.controller;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.*;

import nl.confluent.construction.domain.House;
import nl.confluent.construction.exception.CrashException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;


@RestController
@RequestMapping("/build/house")
public class ConstructionController {
    MeterRegistry registry;

    House house;

    public ConstructionController(MeterRegistry registry, House house) {
        this.registry = registry;
        this.house = house;
    }

    @GetMapping
    // 1.1 Create Timed annotation and call it build.house.timer.annotation
    @Timed("build.house.timer.annotation")
    // 1.2 Create Timed annotation with tags
    @Timed(value = "build.house.timer.annotation",
            extraTags = { "construction", "wood",
                    "construction", "stone"}
    )
    public String build() {
        // call http://localhost:8080/actuator/metrics/build.house.timer.registry?tags=construction:wood

        // 1.3 Create Timer "build.house.timer.metrics" from Metrics class, call record from house.build
        Metrics.timer("build.house.timer.metrics")
                .record(house.build());

        // 1.4 Create Timer "build.house.timer.registry" with Timer.builder at default MeterRegistry registry
        Timer.builder("build.house.timer.registry")
                .tag("construction", "wood")
                .register(registry)
                .record(house.build());

        return "Creating house took " + house.build() + "seconds";
    }

    @GetMapping("/long")
    // 2.1 Create LongTaskTimer and call it "building.house.annotation"
    @Timed(value = "building.house.longtask.annotation", longTask = true)
    public String building() throws InterruptedException {
        house.building();
        return "house build";
    }

    @GetMapping("/used")
    public String used() {
        // 3.1 Create Counter
        Metrics.counter("build.house.counter.wood")
                .increment();

        // 3.2 Create Counter with builder
        Counter.builder("buid.house.counter")
                .tag("used", "wood")
                .tag("used", "stone")
                .register(registry)
                .increment();

        return "Measuring the construction needs";
    }

    @GetMapping("/temperature")
    public String temperature() {

        // 4.1 Create Gauge
        Gauge.builder("buid.house.gauge", House::getTemperature)
                .tags("condition", "hot")
                .strongReference(true)
                .register(Metrics.globalRegistry);

        return "It's hot in here.";
    }

    @GetMapping("/crash")
    public ResponseEntity<Object> crash() {
        // 5.1 Throw an exception and find status in actuator
        //http://localhost:8080/actuator/metrics/http.server.requests?tag=uri:/build/house/crash
        throw new CrashException("oh no crashed");
    }

}
