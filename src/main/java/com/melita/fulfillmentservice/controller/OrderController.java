package com.melita.fulfillmentservice.controller;


import com.melita.fulfillmentservice.dto.OrderDto;
import com.melita.fulfillmentservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/fulfill")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order-update")
    public ResponseEntity<OrderDto> updateOrder(@Valid @RequestBody OrderDto orderDto) {
        return new ResponseEntity<>(orderService.updateAndApprove(orderDto), HttpStatus.OK);
    }

    @PostMapping("/prepare-order")
    public ResponseEntity<OrderDto> fulfillOrder(@RequestParam(value = "orderId") Long orderId) {
        return new ResponseEntity<>(orderService.fulfillOrder(orderId), HttpStatus.OK);
    }

}
