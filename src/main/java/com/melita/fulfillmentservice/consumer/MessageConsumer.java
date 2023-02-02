package com.melita.fulfillmentservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melita.fulfillmentservice.dto.OrderDto;
import com.melita.fulfillmentservice.model.Order;
import com.melita.fulfillmentservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
@Component
@RequiredArgsConstructor
public class MessageConsumer {
    private final ObjectMapper om;
    private final OrderService orderService;

    @RabbitListener(queues = "${rabbitmq.queue.fulfillment}")
    public void newOrderMessage(String message) {
        log.info("Fulfillment service received a new Order message.\n" + message);
        try {
            log.info("Deserialize message.");
            OrderDto orderDTO = om.readValue(message, OrderDto.class);
            Order receivedOrder = orderService.validateAndConvertToOrder(orderDTO);
            orderService.registerPendingOrder(receivedOrder);
            log.info("Pending order registered.");
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deserializing order message");
        }
    }

}
