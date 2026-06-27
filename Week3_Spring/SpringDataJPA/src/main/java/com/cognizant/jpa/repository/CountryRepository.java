package com.cognizant.jpa.repository;

import com.cognizant.jpa.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 
 *
 * JpaRepository provides out-of-the-box:
 *   save(), findById(), findAll(), deleteById(), count(), existsById()
 *
 * - Derived query methods (method name → SQL)
 * - @Query with JPQL
 * - @Query with native SQL
 * - Pagination (Page + Pageable)
 * - @Modifying for UPDATE/DELETE queries
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    // SELECT * FROM countries WHERE country_code = ?
    Optional<Country> findByCode(String code);

    // SELECT * FROM countries WHERE country_name = ?
    Optional<Country> findByName(String name);

    // SELECT * FROM countries WHERE continent = ?
    List<Country> findByContinent(String continent);

    // SELECT * FROM countries WHERE country_name LIKE '%keyword%'
    List<Country> findByNameContainingIgnoreCase(String keyword);

    // SELECT * FROM countries WHERE population > ?
    List<Country> findByPopulationGreaterThan(Long population);

    // SELECT * FROM countries WHERE continent = ? AND population > ?
    List<Country> findByContinentAndPopulationGreaterThan(String continent, Long population);

    // SELECT * FROM countries ORDER BY name ASC
    List<Country> findAllByOrderByNameAsc();

    // EXISTS query
    boolean existsByCode(String code);

    // COUNT query
    long countByContinent(String continent);

    @Query("SELECT c FROM Country c WHERE c.population BETWEEN :min AND :max ORDER BY c.population DESC")
    List<Country> findByPopulationRange(@Param("min") Long min, @Param("max") Long max);

    @Query("SELECT c FROM Country c WHERE LOWER(c.continent) = LOWER(:continent)")
    List<Country> findByContinentIgnoreCase(@Param("continent") String continent);

    @Query(value = "SELECT * FROM countries WHERE area_km2 > :area ORDER BY area_km2 DESC",
           nativeQuery = true)
    List<Country> findLargeCountries(@Param("area") Double area);

    @Query(value = "SELECT continent, COUNT(*) as count FROM countries GROUP BY continent",
           nativeQuery = true)
    List<Object[]> countByContinent();

    // Returns Page<Country> — includes total count, total pages, etc.
    Page<Country> findByContinent(String continent, Pageable pageable);

    Page<Country> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Country c SET c.population = :population WHERE c.code = :code")
    int updatePopulationByCode(@Param("code") String code, @Param("population") Long population);

    @Modifying
    @Transactional
    @Query("DELETE FROM Country c WHERE c.continent = :continent")
    int deleteByContinent(@Param("continent") String continent);
}
