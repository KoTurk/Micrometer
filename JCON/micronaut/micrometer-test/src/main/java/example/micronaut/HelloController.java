package example.micronaut;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Metrics;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

import javax.inject.Singleton;

@Singleton
@Controller("/car/race")
public class HelloController {
    private FormulaOneCar formulaOneCar;
    private PrometheusMeterRegistry meterRegistry;

    public HelloController(PrometheusMeterRegistry meterRegistry, FormulaOneCar formulaOneCar) {
        this.meterRegistry = meterRegistry;
        this.formulaOneCar = formulaOneCar;
    }
    @Get
    @Produces(MediaType.TEXT_PLAIN)
    @Timed(value = "car.race.drive.longtasktimer", longTask = true)
    public String race() throws InterruptedException {
        formulaOneCar.driving();
        return "we're racing";
    }

    @Get("pitstop")
    @Timed("car.race.pitstop.timer.micronaut")
    public String havePitstop() {
        return "pitstop done";
    }

    @Get("fast")
    public String fastestLap() {
        meterRegistry
                .timer("car.race.pitstop.timer2.micronaut")
                .record(formulaOneCar.drivingFast());
        //   Timer.builder("car.race.pitstop.timer").tags("MaxVerstappen").register(registry).record(formulaOneCar.refuel());
        return "Racing fast, you have the fastestLap";
    }



    @Get("laps")
    public String addOneLap() {
        meterRegistry
                .counter("car.race.laps.counter.micronaut")
                .increment();
        return "Added a lap";
    }

    @Get("/tires")
    public void getTireWear() {
        Metrics.gauge("car.race.tire.wear.gauge.micronaut", formulaOneCar.getTireWear());
    }




}
