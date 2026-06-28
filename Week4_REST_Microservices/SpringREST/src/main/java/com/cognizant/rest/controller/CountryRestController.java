package com.cognizant.rest.controller;

import com.cognizant.rest.dto.CountryDTO;
import com.cognizant.rest.entity.Country;
import com.cognizant.rest.exception.CountryNotFoundException;
import com.cognizant.rest.exception.DuplicateCountryException;
import com.cognizant.rest.repository.CountryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryRestController {

    private final CountryRepository countryRepository;

    @GetMapping
    public ResponseEntity<List<CountryDTO>> getAll() {
        List<CountryDTO> list = countryRepository.findAll()
                .stream()
                .map(CountryDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{code}")
    public ResponseEntity<CountryDTO> getByCode(@PathVariable String code) {
        Country c = countryRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new CountryNotFoundException("country not found: " + code));
        return ResponseEntity.ok(CountryDTO.fromEntity(c));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CountryDTO> create(@Valid @RequestBody CountryDTO dto) {
        if (countryRepository.existsByCode(dto.getCode().toUpperCase())) {
            throw new DuplicateCountryException("country already exists: " + dto.getCode());
        }
        Country saved = countryRepository.save(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(CountryDTO.fromEntity(saved));
    }

    @PutMapping("/{code}")
    public ResponseEntity<CountryDTO> update(@PathVariable String code,
                                              @Valid @RequestBody CountryDTO dto) {
        Country existing = countryRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new CountryNotFoundException("country not found: " + code));

        existing.setName(dto.getName());
        existing.setCapital(dto.getCapital());
        existing.setPopulation(dto.getPopulation());

        return ResponseEntity.ok(CountryDTO.fromEntity(countryRepository.save(existing)));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        Country c = countryRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new CountryNotFoundException("country not found: " + code));
        countryRepository.delete(c);
        return ResponseEntity.noContent().build();
    }
}
