package com.melita.fulfillmentservice.service;

import com.melita.fulfillmentservice.exception.ResourceNotFoundException;
import com.melita.fulfillmentservice.exception.response.ErrorCode;
import com.melita.fulfillmentservice.model.Product;
import com.melita.fulfillmentservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void productByIdOrThrow(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty())
            throw new ResourceNotFoundException(String.format("Product id %s does not exist.", productId), ErrorCode.NOT_FOUND);
    }
}
