package nl.blue4it.paymentengine.kafka.basic;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;

@Component
public class PartyModeConsumer {
    Logger log = LoggerFactory.getLogger(PartyModeConsumer.class);
    private String record;

    @KafkaListener(topics = "engine", groupId = "something")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        log.info("received payload='{}'", consumerRecord.value());

        if (consumerRecord.value().equals("Party Mode ON")) {
            setRecord("Engine changed to Party Mode");
        } else {
            setRecord("Engine didn't changed to Party Mode");
        }
    }

    public String getRecord() {
        return record;
    }

    private void setRecord(String value) {
        this.record = value;
    }

}
