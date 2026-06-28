package com.cognizant.rest;

import com.cognizant.rest.entity.Country;
import com.cognizant.rest.repository.CountryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 
 * Integration testing for REST services
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Spring REST API Tests (MockMvc)")
class SpringRestApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        countryRepository.deleteAll();
        countryRepository.save(Country.builder()
                .code("IND").name("India").capital("New Delhi").population(1428000000L).build());
    }

    @Test
    @DisplayName("GET /api/hello — returns Hello World")
    void testHelloWorld() throws Exception {
        mockMvc.perform(get("/api/hello"))
               .andExpect(status().isOk())
               .andExpect(content().string(org.hamcrest.Matchers.containsString("Hello World")));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /api/countries — returns list with HATEOAS links")
    void testGetAllCountries() throws Exception {
        mockMvc.perform(get("/api/countries"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$._embedded.countryDTOList[0].code").value("IND"));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /api/countries/{code} — returns country by code")
    void testGetCountryByCode() throws Exception {
        mockMvc.perform(get("/api/countries/IND"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("India"))
               .andExpect(jsonPath("$.capital").value("New Delhi"));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /api/countries/{code} — 404 for unknown code")
    void testGetCountryByCode_notFound() throws Exception {
        mockMvc.perform(get("/api/countries/XYZ"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/countries — creates a new country")
    void testCreateCountry() throws Exception {
        String json = """
            {"code":"USA","name":"United States","capital":"Washington","population":335000000}
            """;

        mockMvc.perform(post("/api/countries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.code").value("USA"));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/countries — 409 for duplicate code")
    void testCreateCountry_duplicate() throws Exception {
        String json = """
            {"code":"IND","name":"India Duplicate","capital":"Delhi","population":1000000}
            """;

        mockMvc.perform(post("/api/countries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
               .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/countries — 400 for invalid data (validation)")
    void testCreateCountry_validationFails() throws Exception {
        String json = """
            {"code":"","name":"","capital":"","population":-100}
            """;

        mockMvc.perform(post("/api/countries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.fieldErrors").exists());
    }

    @Test
    @WithMockUser
    @DisplayName("PUT /api/countries/{code} — updates existing country")
    void testUpdateCountry() throws Exception {
        String json = """
            {"code":"IND","name":"India Updated","capital":"Mumbai","population":1500000000}
            """;

        mockMvc.perform(put("/api/countries/IND")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("India Updated"))
               .andExpect(jsonPath("$.capital").value("Mumbai"));
    }

    @Test
    @WithMockUser
    @DisplayName("DELETE /api/countries/{code} — removes country")
    void testDeleteCountry() throws Exception {
        mockMvc.perform(delete("/api/countries/IND"))
               .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/countries/IND"))
               .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("POST /api/auth/login — returns JWT for valid credentials")
    void testLogin_success() throws Exception {
        String json = """
            {"username":"mani","password":"password123"}
            """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.token").exists())
               .andExpect(jsonPath("$.type").value("Bearer"))
               .andExpect(jsonPath("$.username").value("mani"));
    }

    @Test
    @DisplayName("POST /api/auth/login — 401 for wrong password")
    void testLogin_wrongPassword() throws Exception {
        String json = """
            {"username":"mani","password":"wrongpass"}
            """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
               .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/countries without JWT — returns 401/403")
    void testProtectedEndpoint_withoutAuth() throws Exception {
        mockMvc.perform(get("/api/countries"))
               .andExpect(status().is4xxClientError());
    }
}
