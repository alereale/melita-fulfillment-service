package com.melita.fulfillmentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.melita.fulfillmentservice.enums.OrderStatus;
import com.melita.fulfillmentservice.model.Customer;
import com.melita.fulfillmentservice.model.Order;
import com.melita.fulfillmentservice.model.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderDto {
    private Long id;

    @JsonProperty(value = "order_number")
    private String orderNumber;

    @JsonProperty(value = "order_status")
    private OrderStatus orderStatus;

    @JsonProperty(value = "customer_id")
    @NotNull(message = "An Order must include Customer ID")
    private Long customerId;

    @NotEmpty(message = "An Order must include products.")
    private List<ProductDto> products;

    @JsonProperty(value = "installation_date")
    @NotNull(message = "Installation Date is required. It must be formatted as \"YYYY-MM-DD\"")
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    @FutureOrPresent(message = "Installation Date cannot be in the past")
    private LocalDate installationDate;

    @JsonProperty(value = "installation_time")
    @NotNull(message = "Installation Time is required. It must be formatted as \"HH:mm\"")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime installationTime;

    @JsonProperty(value = "installation_address")
    @NotBlank(message = "Installation address is required.")
    private String installationAddress;

    public Order toOrder(Customer customer, List<Product> products){
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderProducts(products);
        order.setOrderNumber(this.getOrderNumber());
        order.setOrderStatus(this.getOrderStatus());
        order.setInstallationDate(this.getInstallationDate());
        order.setInstallationTime(this.getInstallationTime());
        order.setInstallationAddress(this.getInstallationAddress());
        return order;
    }

    public OrderDto toOrderDto(Order order){
        List<Product> productList = order.getOrderProducts();
        List<ProductDto> productDtoList = new ArrayList<>();
        productList.forEach(product -> {
            ProductDto productDto = new ProductDto().toProductDto(product);
            productDtoList.add(productDto);
        });

        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setCustomerId(order.getCustomer().getId());
        orderDto.setProducts(productDtoList);
        orderDto.setOrderNumber(order.getOrderNumber());
        orderDto.setOrderStatus(order.getOrderStatus());
        orderDto.setInstallationDate(order.getInstallationDate());
        orderDto.setInstallationTime(order.getInstallationTime());
        orderDto.setInstallationAddress(order.getInstallationAddress());
        return orderDto;
    }
}
