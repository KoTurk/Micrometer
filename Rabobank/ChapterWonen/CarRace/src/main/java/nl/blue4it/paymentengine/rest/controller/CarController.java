package nl.blue4it.paymentengine.rest.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
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
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/car/race")
public class CarController {
    MeterRegistry registry;
    PrometheusMeterRegistry prometheusMeterRegistry;
    FormulaOneCar car;
    PartyModeProducer producer;
    PartyModeConsumer consumer;

    public CarController(MeterRegistry registry, PrometheusMeterRegistry prometheusMeterRegistry, FormulaOneCar car, PartyModeProducer producer, PartyModeConsumer consumer) {
        this.registry = registry;
        this.prometheusMeterRegistry = prometheusMeterRegistry;
        this.car = car;
        this.producer = producer;
        this.consumer = consumer;
    }

    @GetMapping("pitstop")
    // 1.1 Create Timed annotation and call it car.race.pitstop.annotation
//    @Timed("car.race.pitstop.annotation")
    // 2.1 Create Timed annotation with tags
    @Timed(value = "car.race.pitstop.annotation",
                extraTags = { "driver", "verstappen",
                        "changed", "tires",
                        "changed", "front"}
        )
    public String doPitstop() {
        // 1.2 Create Timer "car.race.pitstop.metrics" from Metrics class, call record from car.refuel
        Metrics.timer("car.race.pitstop.metrics")
                .record(car.refuel());

        // 1.3 Create Timer "car.race.pitstop.registry" with Timer.builder at default MeterRegistry registry
        Timer.builder("car.race.pitstop.registry")
                .tag("driver", "maxverstappen")
                .register(registry)
                .record(car.refuel());

        // 1.4 Create Timer from  at PrometheusRegistry to compare against SLA
        Timer.builder("car.race.pitstop.builder.prometheus")
                .sla(Duration.ofSeconds(8))
                .minimumExpectedValue(Duration.ofSeconds(5))
                .maximumExpectedValue(Duration.ofSeconds(10))
                .register(prometheusMeterRegistry)
                .record(ThreadLocalRandom.current().nextLong(3,20), TimeUnit.SECONDS);

        // 2.2 Create timer with tag from Timer.Builder, register and record it
        // call it here http://localhost:8080/actuator/metrics/car.race.pitstop.tag?tag=driver:verstappen
        Timer.builder("car.race.pitstop.tag")
                .tag("driver", "verstappen")
                .tag("brand", "redbull")
                .register(registry)
                .record(car.refuel());

        //pitstop 2 perez
        Timer.builder("car.race.pitstop.tag")
                .tag("driver", "verstappen")
                .register(registry)
                .record(car.refuel());

        // pitstop 3 again perez
        Timer.builder("car.race.pitstop.tag")
                .tag("driver", "verstappen")
                .register(registry)
                .record(car.refuel());

        // 2.3 Create timer with TaggedTimer --> no need to create everytime a timer -> for example when having multiple pitstops
        TaggedTimer perTypeTimer = new TaggedTimer("car.race.pitstop.dynamic", "driver", registry);
        perTypeTimer.getTimer("verstappen").record(Duration.ofSeconds(2));
        perTypeTimer.getTimer("perez").record(Duration.ofSeconds(3));
        perTypeTimer.getTimer("perez").record(Duration.ofSeconds(5));

        return "Pitstop took " + car.refuel() + "seconds";
    }

    @GetMapping()
    // 3.1 Create LongTaskTimer
    @Timed(value = "car.race", longTask = true)
    public String race() throws InterruptedException {
        car.driving();
        return "race is over";
    }

    @GetMapping("/lap")
    public String addLap() {
        // 4.1 Create Counter
        Metrics.counter("car.race.laps").increment();

        return "added a lap";
    }

    @GetMapping("/tires")
    public String tires() {
        final int tireWear = car.getTireWear();

        // 5.1 Create Gauge
        Metrics.gauge("car.race.tires", car.getTireWear());

        return "The lost " + tireWear + "%";
    }

    @GetMapping("/weather")
    public String weather() {
        String weather = randomWeather();

        // 5.2 create a Gauge, with value randomHardness
        Gauge.builder("car.race.weather", this::randomHardness)
                        .tags("condition", weather)
                        .strongReference(true)
                        .register(Metrics.globalRegistry);

        return weather;
    }

    @GetMapping("/crash")
    public ResponseEntity<Object> crash() {
        // 6.1 Throw an exception and find status in actuator
        //http://localhost:8080/actuator/metrics/http.server.requests?tag=uri:/car/race/crash
        throw new CrashException("oh no crashed");
    }

    @GetMapping("/partymode")
    public String enginePartyMode() {
        // 7.1 Sending data to other Kafka topics and find the metrics in actuator
        // after calling the endpoint check metric spring.kafka.listener for the count
        producer.send("engine", "Party Mode ON");

        return consumer.getRecord();
    }

    @GetMapping("/registry")
    public Meter getRegistry() {
        // 8.1 getting the data out of actuator with registry.getMeters.stream
        return registry.getMeters().stream()
                .filter(meter -> meter.getId().getName().equals("http.server.requests"))
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }

    @GetMapping("/remove/fastestlap")
    public String removeFastestLap() {
        // 9.1 delete fastest lap
        String timerName = "car.qualification.remove";
        String tag = "driver";
        String driverVerstappenTag = "verstappen";
        String driverLeClerqTag = "leclerq";

        Timer timerLeClerq = Timer.builder(timerName).tag(tag, driverLeClerqTag).register(Metrics.globalRegistry);
        Timer timerVerstappen = Timer.builder(timerName).tag(tag, driverVerstappenTag).register(Metrics.globalRegistry);

        timerLeClerq.record(Duration.ofSeconds(ThreadLocalRandom.current().nextLong(3,20)));
        timerVerstappen.record(Duration.ofSeconds(ThreadLocalRandom.current().nextLong(3,20)));

        Timer timer = ((Timer) Metrics.globalRegistry.getMeters().stream()
                .filter(meter -> meter.getId().getTag(tag).equals(driverLeClerqTag))
                .findFirst()
                .orElse(Metrics.timer(timerName)));

        // 9.1 remove the fastest lap from the registry
        //  for a driver because crossing a white line
        //  use a method from Metrics.globalRegistry
        registry.remove(timer);

        return "removed";
    }

    private String randomWeather() {
        String[] weatherConditions = {"SUNNY", "RAIN", "CLOUDY", "SNOW"};
        Random random = new Random();
        int randomNumber = random.nextInt(weatherConditions.length);

        return weatherConditions[randomNumber];
    }
    private int randomHardness(){
        return ThreadLocalRandom.current().nextInt(0,100);
    }
}
