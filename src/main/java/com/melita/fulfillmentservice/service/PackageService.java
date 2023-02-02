package com.melita.fulfillmentservice.service;

import com.melita.fulfillmentservice.exception.ResourceNotFoundException;
import com.melita.fulfillmentservice.exception.response.ErrorCode;
import com.melita.fulfillmentservice.model.Package;
import com.melita.fulfillmentservice.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackageService {
    private final PackageRepository packageRepository;

    public Package packageByIdOrThrow(Long packageId) {
        Optional<Package> optionalPackage = packageRepository.findById(packageId);
        if (optionalPackage.isEmpty())
            throw new ResourceNotFoundException(String.format("Package id %s does not exist.", packageId), ErrorCode.NOT_FOUND);
        return optionalPackage.get();
    }
}
