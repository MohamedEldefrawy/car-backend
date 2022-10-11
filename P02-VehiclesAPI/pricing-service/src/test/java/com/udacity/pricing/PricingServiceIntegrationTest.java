package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricingServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private Long port;
    private final String url = "http://localhost:";

    @Test
    public void testPriceApiCrudOperations() {
        // First create price
        var newPrice = new Price();
        newPrice.setVehicleId(1L);
        newPrice.setCurrency("EGP");
        newPrice.setPrice(new BigDecimal(200000));
        var httpEntity = new HttpEntity<Price>(newPrice);
        var response = this.restTemplate.exchange(url + port + "/prices", HttpMethod.POST, httpEntity, Price.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));

        // get by id
        response =
                this.restTemplate.getForEntity(url + port + "/prices/1", Price.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        // update
        newPrice.setCurrency("USD");
        httpEntity = new HttpEntity<>(newPrice);
        this.restTemplate.exchange(url + port + "/prices", HttpMethod.PUT, httpEntity, Price.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        //delete
        this.restTemplate.exchange(url + port + "/prices/1", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

}
