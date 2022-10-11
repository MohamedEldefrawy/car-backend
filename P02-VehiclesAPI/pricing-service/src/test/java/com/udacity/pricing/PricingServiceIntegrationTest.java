package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PricingServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private Long port;
    private final String url = "http://localhost:";


    @Test
    public void lastGetValidPrice() {
        ResponseEntity<Price> response =
                this.restTemplate.exchange(url + port + "/prices/1", HttpMethod.GET, ResponseEntity.EMPTY, Price.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getInvalidPrice() {
        ResponseEntity<Price> response =
                this.restTemplate.getForEntity(url + port + "/prices/123", Price.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void firstInsertVehiclePrice() {
        var newPrice = new Price();
        newPrice.setVehicleId(1L);
        newPrice.setCurrency("EGP");
        newPrice.setPrice(new BigDecimal(200000));
        var httpEntity = new HttpEntity<Price>(newPrice);
        var response = this.restTemplate.exchange(url + port + "/prices", HttpMethod.POST, httpEntity, Price.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }


}
