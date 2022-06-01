package nl.blue4it.paymentengine.kafka.basic;

import nl.blue4it.paymentengine.CarRaceApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext
@SpringBootTest(classes = CarRaceApplication.class)
@KafkaListener(topics = "engine", groupId = "something")
class EmbeddedKafkaIntegrationTest {

    @Autowired
    public KafkaTemplate<String, String> template;

    @Autowired
    private PartyModeConsumer consumer;

    @Autowired
    private PartyModeProducer producer;

    @Value("engine")
    private String topic;

    @Test
    @Disabled // can be deleted if docker instance running. For docker details see folder ../docker
    public void testConsumer() throws Exception {
        producer.send(topic, "Party Mode ON");
        Thread.sleep(5000);
        System.out.println("--------> received message with payload " + consumer.getRecord());
        assertEquals("Engine changed to Party Mode", consumer.getRecord());
    }
}
