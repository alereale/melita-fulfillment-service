package com.melita.fulfillmentservice.service;

import com.melita.fulfillmentservice.dto.OrderDto;
import com.melita.fulfillmentservice.enums.OrderStatus;
import com.melita.fulfillmentservice.exception.BadRequestException;
import com.melita.fulfillmentservice.exception.ResourceNotFoundException;
import com.melita.fulfillmentservice.exception.response.ErrorCode;
import com.melita.fulfillmentservice.model.Customer;
import com.melita.fulfillmentservice.model.Order;
import com.melita.fulfillmentservice.model.Package;
import com.melita.fulfillmentservice.model.Product;
import com.melita.fulfillmentservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerService customerService;
    private final PackageService packageService;
    private final ProductService productService;
    private final OrderRepository orderRepository;

    @Transactional(rollbackFor = Exception.class)
    public void registerPendingOrder(Order pendingOrder) {
        log.info("Saving pending order.");
        orderRepository.save(pendingOrder);
    }

    public Order validateAndConvertToOrder(OrderDto orderDto) {
        List<Product> products = new ArrayList<>();

        log.info("Validate order");
        orderDto.getProducts().forEach(productDto -> {
            log.info("Check if product exist - id: " + productDto.getId());
            productService.productByIdOrThrow(productDto.getId());

            productDto.getPackages().forEach(pkg -> {
                log.info("Check if package exist - id: " + pkg.getId());
                Package storedPackage = packageService.packageByIdOrThrow(pkg.getId());

                log.info(String.format("Check if package id %s belongs to product id %s", pkg.getId(), productDto.getId()));
                if(!storedPackage.getProduct().getId().equals(productDto.getId()))
                    throw new BadRequestException(String.format("Package %s cannot be associated to Product %s.", pkg.getName(), productDto.getCategory()), ErrorCode.BAD_REQUEST);
            });
            products.add(productDto.toProduct());
        });

        log.info("Check if customer exist - id: " + orderDto.getCustomerId());
        Customer customer = customerService.customerByIdOrThrow(orderDto.getCustomerId());

        log.info("Order validation complete.");

        return orderDto.toOrder(customer, products);
    }

    @Transactional(rollbackFor = Exception.class)
    public OrderDto fulfillOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty())
            throw new ResourceNotFoundException(String.format("Order id %s does not exist.", id), ErrorCode.NOT_FOUND);

        Order storedOrder = optionalOrder.get();
        if (storedOrder.getOrderStatus().equals(OrderStatus.READY))
            throw new BadRequestException(ErrorCode.BAD_REQUEST, "Order n. " + storedOrder.getOrderNumber() + " already fulfilled");

        storedOrder.setOrderStatus(OrderStatus.READY);

        Order fulfilledOrder = orderRepository.save(storedOrder);
        return new OrderDto().toOrderDto(fulfilledOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    public OrderDto updateAndApprove(OrderDto orderDto) {
        Optional<Order> optionalOrder = orderRepository.findById(orderDto.getId());
        if (optionalOrder.isEmpty())
            throw new ResourceNotFoundException(String.format("Order id %s does not exist.", orderDto.getId()), ErrorCode.NOT_FOUND);
        Order storedOrder = optionalOrder.get();
        if (storedOrder.getOrderStatus().equals(OrderStatus.READY))
            throw new BadRequestException(ErrorCode.BAD_REQUEST, "Fulfillment is already processing the order n. " + storedOrder.getOrderNumber());

        Order validatedOrder = validateAndConvertToOrder(orderDto);

        validatedOrder.setId(optionalOrder.get().getId());
        validatedOrder.setOrderNumber(optionalOrder.get().getOrderNumber());
        validatedOrder.setOrderStatus(OrderStatus.APPROVED);
        validatedOrder.setInstallationDate(orderDto.getInstallationDate());
        validatedOrder.setInstallationTime(orderDto.getInstallationTime());
        validatedOrder.setInstallationAddress(orderDto.getInstallationAddress());

        Order updatedOrder = orderRepository.save(validatedOrder);
        log.info("Order updated!");
        return new OrderDto().toOrderDto(updatedOrder);
    }
}
