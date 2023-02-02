package com.melita.fulfillmentservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "First name is required")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Last Name is required")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Email is required")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;
}
