package nl.confluent.construction.domain;

import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class House {

    public static int getTemperature() {
        return 35;
    }

    public Duration build() {
        return Duration.ofSeconds(8);
    }

    public void building() throws InterruptedException {
        Thread.sleep((long) (Math.random() *200000));
    }
}

