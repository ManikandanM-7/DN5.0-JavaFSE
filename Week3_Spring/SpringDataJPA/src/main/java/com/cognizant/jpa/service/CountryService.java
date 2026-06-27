package com.cognizant.jpa.service;

import com.cognizant.jpa.entity.Country;
import com.cognizant.jpa.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CountryService {

    private final CountryRepository countryRepository;

    @Transactional
    public Country save(Country country) {
        log.info("saving country: {}", country.getName());
        return countryRepository.save(country);
    }

    @Transactional
    public List<Country> saveAll(List<Country> countries) {
        return countryRepository.saveAll(countries);
    }

    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }

    public Country findByIdOrThrow(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found: " + id));
    }

    public Optional<Country> findByCode(String code) {
        return countryRepository.findByCode(code.toUpperCase());
    }

    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    public List<Country> findAllSorted(String sortBy, String dir) {
        Sort sort = dir.equalsIgnoreCase("DESC")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return countryRepository.findAll(sort);
    }

    public List<Country> findByContinent(String continent) {
        return countryRepository.findByContinent(continent);
    }

    public List<Country> searchByName(String keyword) {
        return countryRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Country> findByPopulationRange(Long min, Long max) {
        return countryRepository.findByPopulationRange(min, max);
    }

    public Page<Country> findAllPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return countryRepository.findAll(pageable);
    }

    public Page<Country> findByContinentPaginated(String continent, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return countryRepository.findByContinent(continent, pageable);
    }

    @Transactional
    public Country update(Long id, Country updated) {
        Country existing = findByIdOrThrow(id);
        existing.setName(updated.getName());
        existing.setCapital(updated.getCapital());
        existing.setContinent(updated.getContinent());
        existing.setPopulation(updated.getPopulation());
        existing.setArea(updated.getArea());
        return countryRepository.save(existing);
    }

    @Transactional
    public int updatePopulation(String code, Long population) {
        return countryRepository.updatePopulationByCode(code, population);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new RuntimeException("Country not found: " + id);
        }
        countryRepository.deleteById(id);
    }

    public long count() {
        return countryRepository.count();
    }

    public boolean existsByCode(String code) {
        return countryRepository.existsByCode(code.toUpperCase());
    }

    public long countByContinent(String continent) {
        return countryRepository.countByContinent(continent);
    }
}
