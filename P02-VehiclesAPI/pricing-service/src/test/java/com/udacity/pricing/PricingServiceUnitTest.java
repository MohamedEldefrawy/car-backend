package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.exception.InsertPriceToVehicleException;
import com.udacity.pricing.exception.PriceNotFoundException;
import com.udacity.pricing.repository.PriceRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PricingServiceUnitTest {

    @Autowired
    private PriceRepository priceRepository;

    @Test
    public void testCreatePrice() {
        var price = new Price();
        price.setVehicleId(1L);
        price.setCurrency("EGP");
        price.setPrice(new BigDecimal(200000));

        Assertions.assertDoesNotThrow(() -> {
            this.priceRepository.save(price);
        });
    }

    @Test
    public void testUpdatePrice() {
        var price = new Price();
        price.setVehicleId(1000L);
        price.setCurrency("USD");
        price.setPrice(new BigDecimal(200000));

        this.priceRepository.save(price);


        price.setCurrency("EGP");
        Assertions.assertDoesNotThrow(() -> {
            this.priceRepository.save(price);
        });
    }

    @Test
    public void testDeletePrice() {
        var price = new Price();
        price.setVehicleId(103L);
        price.setCurrency("USD");
        price.setPrice(new BigDecimal(200000));

        this.priceRepository.save(price);

        Assertions.assertDoesNotThrow(() -> {
            this.priceRepository.deleteById(price.getVehicleId());
        });
    }
}
