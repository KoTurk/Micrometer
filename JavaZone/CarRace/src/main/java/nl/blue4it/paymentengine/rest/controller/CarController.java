package nl.blue4it.paymentengine.rest.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import nl.blue4it.paymentengine.kafka.basic.PartyModeConsumer;
import nl.blue4it.paymentengine.kafka.basic.PartyModeProducer;
import nl.blue4it.paymentengine.rest.domain.FormulaOneCar;
import nl.blue4it.paymentengine.rest.domain.TaggedTimer;
import nl.blue4it.paymentengine.rest.exception.CrashException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/car/race")
public class CarController {
    MeterRegistry registry;
    PrometheusMeterRegistry prometheusMeterRegistry;
    FormulaOneCar car;
    PartyModeProducer producer;
    PartyModeConsumer consumer;

    public CarController(MeterRegistry registry, FormulaOneCar car, PartyModeProducer producer, PartyModeConsumer consumer) {
        this.registry = registry;
        this.car = car;
        this.producer = producer;
        this.consumer = consumer;
    }

    @GetMapping("pitstop")
    // 1.1 Create Timed annotation and call it car.race.pitstop
    public String doPitstop() {
        // 1.2 Create Timer "car.race.pitstop.metrics" from Metrics class, call record from car.refuel

        // 1.3 Create Timer "car.race.pitstop.registry" with Timer.builder at default MeterRegistry registry

        // 1.4 Create Timer from  at PrometheusRegistry to compare against SLA

        // 2.1 Create timer with tag from Timer.Builder, register and record it
        // call it here http://localhost:8080/actuator/metrics/car.race.pitstop.tag?tag=driver:verstappen

        //pitstop 2

        // pitstop 3

        // 2.2 Create timer with TaggedTimer --> no need to create everytime a timer -> for example when having multiple pitstops

        return "We did a pitstop in " + car.refuel().toSeconds() + " seconds";
    }

    @GetMapping()
    // 3.1 Create LongTaskTimer
    public String race() throws InterruptedException {
        car.driving();
        return "race is over";
    }

    @GetMapping("/lap")
    public String addLap() {
        // 4.1 Create Counter
        return "added a lap";
    }

    @GetMapping("/tires")
    public String tires() {
        final int tireWear = car.getTireWear();

        // 5.1 Create Gauge

        return "The lost " + tireWear + "%";
    }

    @GetMapping("/crash")
    public ResponseEntity<Object> crash() {
        // 6.1 Throw an exception and find status in actuator
        //http://localhost:8080/actuator/metrics/http.server.requests?tag=uri:/car/race/crash
        return null;
    }

    @GetMapping("/partymode")
    public String enginePartyMode() {
        // 7.1 Sending data to other Kafka topics and find the metrics in actuator
        producer.send("engine", "Party Mode ON");
        return consumer.getRecord();
    }

    @GetMapping("/registry")
    public Meter getRegistry() {
        // 8.1 getting the data out of actuator
        return registry.getMeters().stream()
               .filter(meter -> meter.getId().getName().equals("http.server.requests"))
               .findFirst()
               .orElseThrow(NullPointerException::new);
    }


}
