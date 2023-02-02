package com.melita.fulfillmentservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "category is required")
    private String category;

    @ManyToMany(mappedBy = "orderProducts")
    private List<Order> orders;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Package> packages = new ArrayList<>();

    public void addPackages(List<Package> packages) {
        this.packages = packages;
        this.packages.forEach(pkg -> pkg.setProduct(this));
    }
}
