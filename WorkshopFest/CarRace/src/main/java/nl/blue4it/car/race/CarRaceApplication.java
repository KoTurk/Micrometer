package nl.blue4it.car.race;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarRaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRaceApplication.class, args);
    }

}
