package com.udacity.pricing;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PricingServiceUnitTests {
    @InjectMocks
    private MockMvc mockMvc;

    @Test
    public void getPriceWithNotValidId() throws Exception {
        this.mockMvc.perform(get("http://modafro:8082/services/price?vehicleId=123"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPriceWithValidId() throws Exception {
        var response = this.mockMvc.perform(get("http://modafro:8082/services/price?vehicleId=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        Assertions.assertTrue(response.contains("price"));
    }
}
