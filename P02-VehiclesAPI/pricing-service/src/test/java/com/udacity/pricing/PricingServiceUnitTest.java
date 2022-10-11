package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.exception.InsertPriceToVehicleException;
import com.udacity.pricing.exception.PriceNotFoundException;
import com.udacity.pricing.service.PriceService;
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
    private PriceService priceService;

    @Test
    public void testCreatePrice() {
        var price = new Price();
        price.setVehicleId(1L);
        price.setCurrency("EGP");
        price.setPrice(new BigDecimal(200000));

        Assertions.assertDoesNotThrow(() -> {
            this.priceService.insert(price);
        });
    }

    @Test
    public void testCreatePriceWithDuplicatedId() {
        var price = new Price();
        price.setVehicleId(1500L);
        price.setCurrency("EGP");
        price.setPrice(new BigDecimal(200000));
        try {
            this.priceService.insert(price);
        } catch (InsertPriceToVehicleException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertThrows(InsertPriceToVehicleException.class, () -> {
            this.priceService.insert(price);
        });
    }

    @Test
    public void testUpdatePrice() {
        var price = new Price();
        price.setVehicleId(1000L);
        price.setCurrency("USD");
        price.setPrice(new BigDecimal(200000));

        try {
            this.priceService.insert(price);
        } catch (InsertPriceToVehicleException e) {
            throw new RuntimeException(e);
        }

        price.setCurrency("EGP");
        Assertions.assertDoesNotThrow(() -> {
            this.priceService.update(price);
        });
    }

    @Test
    public void testUpdatePriceWithInvalidId() {
        var price = new Price();
        price.setVehicleId(104L);
        price.setCurrency("USD");
        price.setPrice(new BigDecimal(200000));

        try {
            this.priceService.insert(price);
        } catch (InsertPriceToVehicleException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertThrows(PriceNotFoundException.class, () -> {
            price.setVehicleId(10000L);
            this.priceService.update(price);
        });
    }

    @Test
    public void testDeletePrice() {
        var price = new Price();
        price.setVehicleId(103L);
        price.setCurrency("USD");
        price.setPrice(new BigDecimal(200000));

        try {
            this.priceService.insert(price);
        } catch (InsertPriceToVehicleException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertDoesNotThrow(() -> {
            this.priceService.delete(price.getVehicleId());
        });
    }

    @Test
    public void testDeletePriceWithInvalidId() {
        var price = new Price();
        price.setVehicleId(102L);
        price.setCurrency("USD");
        price.setPrice(new BigDecimal(200000));

        try {
            this.priceService.insert(price);
        } catch (InsertPriceToVehicleException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertThrows(PriceNotFoundException.class, () -> {
            this.priceService.delete(57357l);
        });
    }
}
