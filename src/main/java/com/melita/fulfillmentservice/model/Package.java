package com.melita.fulfillmentservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@Table(name = "packages")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "name is required")
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
