package com.udacity.pricing.controller;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.service.PriceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prices")
public class PriceController {
    private PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping
    public ResponseEntity<Price> create(@RequestParam Price price) {
        return new ResponseEntity<>(this.priceService.save(price), HttpStatus.CREATED);
    }

    @DeleteMapping
    public void delete(@RequestParam Long vehicleId) {
        this.priceService.delete(vehicleId);
    }
}
