package com.melita.fulfillmentservice.consumer;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MessageConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.fulfillment}")
    public void newOrderMessage(String message) {
        log.info("Fulfillment received the Order..\n" + message);
    }

}
