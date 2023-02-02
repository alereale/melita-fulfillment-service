package com.melita.fulfillmentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.melita.fulfillmentservice.model.Product;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductDto {
    @NotNull
    private Long id;

    @NotBlank
    private String category;

    @NotEmpty(message = "A product must include at least one package.")
    private List<PackageDto> packages = new ArrayList<>();

    public Product toProduct(){
        Product product = new Product();
        product.setId(this.id);
        product.setCategory(this.getCategory());
        product.addPackages(this.packages.stream()
                .map(packages -> packages.toPackage(product))
                .toList()
        );
        return product;
    }

    public ProductDto toProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setCategory(product.getCategory());

        List<PackageDto> packages = new ArrayList<>();
        product.getPackages()
                .forEach(pkg -> packages.add(new PackageDto().toPackageDto(pkg)));
        productDto.setPackages(packages);

        return productDto;
    }

}
