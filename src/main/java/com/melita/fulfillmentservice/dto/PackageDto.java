package com.melita.fulfillmentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.melita.fulfillmentservice.model.Package;
import com.melita.fulfillmentservice.model.Product;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PackageDto {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

    public Package toPackage(Product product){
        Package productPackage = new Package();
        productPackage.setId(this.id);;
        productPackage.setName(this.getName());
        productPackage.setProduct(product);
        return productPackage;
    }

    public PackageDto toPackageDto(Package pkg){
        PackageDto productPackage = new PackageDto();
        productPackage.setId(pkg.getId());
        productPackage.setName(pkg.getName());
        return productPackage;
    }
}
