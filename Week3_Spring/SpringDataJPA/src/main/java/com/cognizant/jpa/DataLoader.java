package com.cognizant.jpa;

import com.cognizant.jpa.entity.Country;
import com.cognizant.jpa.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DataLoader — seeds Country data on application startup
 * Implements CommandLineRunner → runs after Spring context loads
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final CountryService countryService;

    @Override
    public void run(String... args) {
        log.info("Seeding initial country data...");

        List<Country> countries = List.of(
            Country.builder().code("IND").name("India")          .capital("New Delhi") .continent("Asia")    .population(1428627663L).area(3287263.0).build(),
            Country.builder().code("CHN").name("China")          .capital("Beijing")   .continent("Asia")    .population(1425671352L).area(9596960.0).build(),
            Country.builder().code("USA").name("United States")  .capital("Washington").continent("Americas").population(335893238L) .area(9833517.0).build(),
            Country.builder().code("BRA").name("Brazil")         .capital("Brasília")  .continent("Americas").population(216422446L) .area(8515767.0).build(),
            Country.builder().code("GBR").name("United Kingdom") .capital("London")    .continent("Europe")  .population(67736802L)  .area(242495.0) .build(),
            Country.builder().code("FRA").name("France")         .capital("Paris")     .continent("Europe")  .population(68170228L)  .area(551695.0) .build(),
            Country.builder().code("DEU").name("Germany")        .capital("Berlin")    .continent("Europe")  .population(83294633L)  .area(357114.0) .build(),
            Country.builder().code("JPN").name("Japan")          .capital("Tokyo")     .continent("Asia")    .population(123294513L) .area(377975.0) .build(),
            Country.builder().code("AUS").name("Australia")      .capital("Canberra")  .continent("Oceania") .population(26439111L)  .area(7692024.0).build(),
            Country.builder().code("ZAF").name("South Africa")   .capital("Cape Town") .continent("Africa")  .population(60414495L)  .area(1221037.0).build()
        );

        countryService.saveAll(countries);
        log.info("Seeded {} countries. Total in DB: {}", countries.size(), countryService.count());

        // Demo: show pagination working
        log.info("First 3 countries (alphabetical): {}",
                 countryService.findAllPaginated(0, 3, "name")
                               .getContent()
                               .stream()
                               .map(Country::getName)
                               .toList());
    }
}
