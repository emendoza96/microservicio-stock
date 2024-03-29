package com.microservice.stock.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.microservice.stock.dao.MaterialRepository;
import com.microservice.stock.dao.ProvisionRepository;
import com.microservice.stock.domain.Material;
import com.microservice.stock.domain.Provision;
import com.microservice.stock.domain.ProvisionDetail;
import com.microservice.stock.domain.Unit;
import com.microservice.stock.security.jwt.JwtUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProvisionRepository provisionRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private JwtUtils jwtUtils;

    private HttpHeaders headers;

    private Material material1;
    private Material material2;
    private Provision provision1;
    private Provision provision2;
    private ProvisionDetail detail1;
    private ProvisionDetail detail2;
    private ProvisionDetail detail3;

    @BeforeEach
    void setUp() {
        String token = "Bearer " + jwtUtils.generateAccessToken("emi123");
        headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        material1 = Material.builder()
            .name("Brick")
            .description("Test description")
            .currentStock(100)
            .price(5.5d)
            .unit(new Unit())
            .build()
        ;

        material2 = Material.builder()
            .name("Brick 2")
            .description("Test description 2")
            .currentStock(800)
            .price(8.5d)
            .unit(new Unit())
            .build()
        ;

        // Provision 1

        provision1 = Provision.builder()
            .provisionDate(LocalDate.now())
            .build()
        ;

        detail1 = ProvisionDetail.builder()
            .quantity(100)
            .provision(provision1)
            .build()
        ;

        detail2 = ProvisionDetail.builder()
            .quantity(200)
            .provision(provision1)
            .build()
        ;

        // Provision 2

        provision2 = Provision.builder()
            .provisionDate(LocalDate.of(2022, 12, 2))
            .build()
        ;

        detail3 = ProvisionDetail.builder()
            .quantity(300)
            .provision(provision2)
            .build()
        ;
    }

    @Test
    void testGetProvisions() {
        Material materialResult1 = materialRepository.save(material1);
        Material materialResult2 = materialRepository.save(material2);
        detail1.setMaterial(materialResult1);
        detail2.setMaterial(materialResult2);
        detail3.setMaterial(materialResult2);
        provision1.setDetail(List.of(detail1, detail2));
        provision2.setDetail(List.of(detail3));
        provisionRepository.save(provision1);
        provisionRepository.save(provision2);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Provision[]> response = restTemplate.exchange(
            "/api/stock/provision",
            HttpMethod.GET,
            entity,
            Provision[].class
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);

        List<Provision> provisions = List.of(response.getBody());
        assertThat(provisions.size()).isEqualTo(2);
    }


    @Test
    void testGetProvisionsByDate() {
        Material materialResult1 = materialRepository.save(material1);
        Material materialResult2 = materialRepository.save(material2);
        detail1.setMaterial(materialResult1);
        detail2.setMaterial(materialResult2);
        detail3.setMaterial(materialResult2);
        provision1.setDetail(List.of(detail1, detail2));
        provision2.setDetail(List.of(detail3));
        provisionRepository.save(provision1);
        provisionRepository.save(provision2);

        LocalDate startDate = LocalDate.of(2023, 12, 2);
        LocalDate endDate = LocalDate.of(2025, 12, 2);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Provision[]> response = restTemplate.exchange(
            "/api/stock/provision?startDate={startDate}&endDate={endDate}",
            HttpMethod.GET,
            entity,
            Provision[].class,
            startDate,
            endDate
        );

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);

        List<Provision> provisions = List.of(response.getBody());
        assertThat(provisions.size()).isEqualTo(1);
    }

    @AfterEach
    void clearDB() {
        provisionRepository.deleteAll();
        materialRepository.deleteAll();
    }
}
