package com.cognizant.jpa.controller;

import com.cognizant.jpa.entity.Country;
import com.cognizant.jpa.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 
 *
 * REST API for Country CRUD operations
 *
 * Endpoints:
 *   GET    /api/countries              → list all
 *   GET    /api/countries/{id}         → find by id
 *   GET    /api/countries/code/{code}  → find by country code
 *   GET    /api/countries/continent/{c}→ filter by continent
 *   GET    /api/countries/search?q=    → search by name
 *   GET    /api/countries/paginated    → paginated list
 *   POST   /api/countries              → create
 *   PUT    /api/countries/{id}         → update
 *   PATCH  /api/countries/{id}/population → update population
 *   DELETE /api/countries/{id}         → delete
 *   GET    /api/countries/stats        → count stats
 */
@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
@Slf4j
public class CountryController {

    private final CountryService countryService;

    // get all
    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries(
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC")  String direction) {
        log.info("GET /api/countries?sortBy={}&direction={}", sortBy, direction);
        return ResponseEntity.ok(countryService.findAllSorted(sortBy, direction));
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        log.info("GET /api/countries/{}", id);
        return countryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // get by code
    @GetMapping("/code/{code}")
    public ResponseEntity<Country> getByCode(@PathVariable String code) {
        return countryService.findByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // filter continent
    @GetMapping("/continent/{continent}")
    public ResponseEntity<List<Country>> getByContinent(@PathVariable String continent) {
        List<Country> countries = countryService.findByContinent(continent);
        return ResponseEntity.ok(countries);
    }

    // search
    @GetMapping("/search")
    public ResponseEntity<List<Country>> search(@RequestParam String q) {
        log.info("GET /api/countries/search?q={}", q);
        return ResponseEntity.ok(countryService.searchByName(q));
    }

    // pagination
    @GetMapping("/paginated")
    public ResponseEntity<Page<Country>> getPaginated(
            @RequestParam(defaultValue = "0")    int page,
            @RequestParam(defaultValue = "5")    int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        return ResponseEntity.ok(countryService.findAllPaginated(page, size, sortBy));
    }

    // create
    @PostMapping
    public ResponseEntity<Country> create(@RequestBody Country country) {
        log.info("POST /api/countries - {}", country.getName());
        Country saved = countryService.save(country);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<Country> update(@PathVariable Long id,
                                          @RequestBody Country country) {
        log.info("PUT /api/countries/{}", id);
        try {
            return ResponseEntity.ok(countryService.update(id, country));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // patch
    @PatchMapping("/{id}/population")
    public ResponseEntity<Map<String, Object>> updatePopulation(
            @PathVariable Long id,
            @RequestBody Map<String, Long> body) {
        Country country = countryService.findByIdOrThrow(id);
        int updated = countryService.updatePopulation(country.getCode(), body.get("population"));
        return ResponseEntity.ok(Map.of(
            "updated", updated,
            "country", country.getName(),
            "newPopulation", body.get("population")
        ));
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        try {
            countryService.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "Country deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // stats
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(Map.of(
            "totalCountries", countryService.count(),
            "asiaCount",      countryService.countByContinent("Asia"),
            "europeCount",    countryService.countByContinent("Europe")
        ));
    }
}
