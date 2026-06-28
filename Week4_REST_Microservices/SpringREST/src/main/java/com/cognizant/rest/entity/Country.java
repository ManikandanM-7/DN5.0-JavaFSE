package com.cognizant.rest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Country Entity — used for  "Country Web Service" exercise
 */
@Entity
@Table(name = "countries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Country code is required")
    @Size(min = 2, max = 3, message = "Country code must be 2-3 characters")
    @Column(unique = true, nullable = false, length = 3)
    private String code;

    @NotBlank(message = "Country name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Capital is required")
    private String capital;

    @Positive(message = "Population must be positive")
    private Long population;
}
