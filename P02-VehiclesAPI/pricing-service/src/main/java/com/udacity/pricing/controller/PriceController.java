package com.udacity.pricing.controller;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.exception.InsertPriceToVehicleException;
import com.udacity.pricing.exception.PriceNotFoundException;
import com.udacity.pricing.service.PriceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/prices")
public class PriceController {
    private PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping
    public ResponseEntity<Price> create(@RequestBody Price price) {
        try {
            return new ResponseEntity<>(this.priceService.insert(price), HttpStatus.CREATED);
        } catch (InsertPriceToVehicleException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<Void> delete(@PathVariable Long vehicleId) {
        try {
            this.priceService.delete(vehicleId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (PriceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<Price> get(@PathVariable Long vehicleId) {
        try {
            return new ResponseEntity<>(this.priceService.get(vehicleId), HttpStatus.OK);
        } catch (PriceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Price> update(@RequestBody Price price) {
        try {
            return new ResponseEntity<Price>(this.priceService.update(price), HttpStatus.OK);
        } catch (PriceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
