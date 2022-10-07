package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PricingServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getValidPrice() {
        ResponseEntity<Price> response =
                this.restTemplate.getForEntity("http://modafro:8082/" +"services/price?vehicleId=1", Price.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getInValidPrice() {
        ResponseEntity<Price> response =
                this.restTemplate.getForEntity("http://modafro:8082/" +"services/price?vehicleId=123", Price.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }


}
