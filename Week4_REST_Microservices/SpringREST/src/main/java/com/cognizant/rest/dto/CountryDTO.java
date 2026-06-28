package com.cognizant.rest.dto;

import com.cognizant.rest.entity.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 
 *
 * DTO (Data Transfer Object) — decouples the API contract from the
 * internal entity structure. Useful for:
 * - Hiding internal fields (e.g., id, audit fields)
 * - Versioning APIs without changing entities
 * - Customizing JSON serialization
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO {

    private String code;
    private String name;
    private String capital;
    private Long   population;

    // Entity → DTO
    public static CountryDTO fromEntity(Country country) {
        return CountryDTO.builder()
                .code(country.getCode())
                .name(country.getName())
                .capital(country.getCapital())
                .population(country.getPopulation())
                .build();
    }

    // DTO → Entity
    public Country toEntity() {
        return Country.builder()
                .code(code)
                .name(name)
                .capital(capital)
                .population(population)
                .build();
    }
}
