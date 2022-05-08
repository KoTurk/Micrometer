package nl.blue4it.paymentengine.rest.domain;

import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class FormulaOneCar {

    public int getTireWear() {
        return 50;
    }

    public void refuelAndWait() throws InterruptedException {
        Thread.sleep((long) (Math.random() *2000));
    }

    public Duration refuel() {
        return Duration.ofSeconds(8);
    }

    public void driving() throws InterruptedException {
        Thread.sleep((long) (Math.random() *200000));
    }
}
