package com.cognizant.jpa;

import com.cognizant.jpa.entity.Country;
import com.cognizant.jpa.repository.CountryRepository;
import com.cognizant.jpa.service.CountryService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Spring Data JPA Integration Tests
 * @SpringBootTest loads full application context
 * @Transactional rolls back after each test — keeps DB clean
 */
@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayName("Spring Data JPA Tests")
class CountryServiceTest {

    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryRepository countryRepository;

    private Country india;
    private Country usa;

    @BeforeEach
    void setUp() {
        countryRepository.deleteAll();

        india = countryService.save(Country.builder()
                .code("IND").name("India").capital("New Delhi")
                .continent("Asia").population(1428000000L).area(3287263.0).build());

        usa = countryService.save(Country.builder()
                .code("USA").name("United States").capital("Washington")
                .continent("Americas").population(335000000L).area(9833517.0).build());

        countryService.save(Country.builder()
                .code("CHN").name("China").capital("Beijing")
                .continent("Asia").population(1425000000L).area(9596960.0).build());
    }
    @Test
    @DisplayName("Save country — persisted with auto-generated id")
    void testSave_persistsCountry() {
        Country germany = countryService.save(Country.builder()
                .code("DEU").name("Germany").capital("Berlin")
                .continent("Europe").population(83000000L).build());
        assertNotNull(germany.getId());
        assertEquals("Germany", germany.getName());
    }

    @Test
    @DisplayName("FindById — returns correct country")
    void testFindById_returnsCountry() {
        Optional<Country> found = countryService.findById(india.getId());
        assertTrue(found.isPresent());
        assertEquals("India", found.get().getName());
        assertEquals("IND",   found.get().getCode());
    }

    @Test
    @DisplayName("FindByCode — returns correct country")
    void testFindByCode_returnsCountry() {
        Optional<Country> found = countryService.findByCode("IND");
        assertTrue(found.isPresent());
        assertEquals("New Delhi", found.get().getCapital());
    }

    @Test
    @DisplayName("FindByContinent — returns filtered list")
    void testFindByContinent_filtersCorrectly() {
        List<Country> asian = countryService.findByContinent("Asia");
        assertEquals(2, asian.size());
        assertTrue(asian.stream().allMatch(c -> c.getContinent().equals("Asia")));
    }

    @Test
    @DisplayName("SearchByName — case-insensitive search")
    void testSearchByName_caseInsensitive() {
        List<Country> results = countryService.searchByName("ind");
        assertFalse(results.isEmpty());
        assertTrue(results.stream().anyMatch(c -> c.getName().equals("India")));
    }

    @Test
    @DisplayName("FindAll sorted — returns all in alphabetical order")
    void testFindAllSorted_alphabetical() {
        List<Country> sorted = countryService.findAllSorted("name", "ASC");
        assertEquals(3, sorted.size());
        assertEquals("China",         sorted.get(0).getName());
        assertEquals("India",         sorted.get(1).getName());
        assertEquals("United States", sorted.get(2).getName());
    }

    @Test
    @DisplayName("Update — modifies existing country")
    void testUpdate_modifiesCountry() {
        Country updated = Country.builder()
                .name("India Updated").capital("Mumbai")
                .continent("Asia").population(1500000000L).build();
        Country result = countryService.update(india.getId(), updated);
        assertEquals("India Updated", result.getName());
        assertEquals("Mumbai",        result.getCapital());
    }

    @Test
    @DisplayName("Delete — removes country from DB")
    void testDelete_removesCountry() {
        long before = countryService.count();
        countryService.deleteById(usa.getId());
        assertEquals(before - 1, countryService.count());
        assertFalse(countryService.findById(usa.getId()).isPresent());
    }

    @Test
    @DisplayName("ExistsById — returns true for existing")
    void testExistsByCode_returnsTrue() {
        assertTrue(countryService.existsByCode("IND"));
        assertFalse(countryService.existsByCode("XYZ"));
    }
    @Test
    @DisplayName("Pagination — returns correct page size")
    void testFindAllPaginated_returnsCorrectPageSize() {
        Page<Country> page = countryService.findAllPaginated(0, 2, "name");
        assertEquals(2, page.getNumberOfElements());
        assertEquals(3, page.getTotalElements());
        assertEquals(2, page.getTotalPages());
        assertTrue(page.isFirst());
    }

    @Test
    @DisplayName("Pagination page 2 — returns remaining items")
    void testFindAllPaginated_secondPage() {
        Page<Country> page = countryService.findAllPaginated(1, 2, "name");
        assertEquals(1, page.getNumberOfElements());
        assertTrue(page.isLast());
    }
    @Test
    @DisplayName("PopulationRange — returns countries in range")
    void testFindByPopulationRange() {
        List<Country> result = countryService.findByPopulationRange(300_000_000L, 1_500_000_000L);
        assertFalse(result.isEmpty());
        result.forEach(c ->
            assertTrue(c.getPopulation() >= 300_000_000L && c.getPopulation() <= 1_500_000_000L)
        );
    }

    @Test
    @DisplayName("UpdatePopulation — JPQL @Modifying updates row")
    void testUpdatePopulation_jpqlModifying() {
        int rowsUpdated = countryService.updatePopulation("IND", 1_500_000_000L);
        assertEquals(1, rowsUpdated);
    }

    @Test
    @DisplayName("CountByContinent — returns correct count")
    void testCountByContinent() {
        assertEquals(2, countryService.countByContinent("Asia"));
        assertEquals(1, countryService.countByContinent("Americas"));
        assertEquals(0, countryService.countByContinent("Antarctica"));
    }

    @Test
    @DisplayName("FindByIdOrThrow — throws for missing id")
    void testFindByIdOrThrow_throwsForMissing() {
        assertThrows(RuntimeException.class,
            () -> countryService.findByIdOrThrow(9999L));
    }
}
