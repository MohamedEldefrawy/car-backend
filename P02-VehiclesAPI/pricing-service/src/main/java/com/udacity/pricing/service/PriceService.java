package com.udacity.pricing.service;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.exception.PriceNotFoundException;
import com.udacity.pricing.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {
    private PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public List<Price> getAll() throws PriceNotFoundException {
        var prices = this.priceRepository.findAll();
        if (prices.size() == 0) {
            throw new PriceNotFoundException("No Prices Found");
        }
        return prices;
    }

    public Price get(Long vehicleId) throws PriceNotFoundException {
        var priceOptional = this.priceRepository.findById(vehicleId);

        if (!priceOptional.isPresent()) {
            throw new PriceNotFoundException("Price Not Found");
        }

        return priceOptional.get();
    }

    public Price save(Price price) {
        return this.priceRepository.save(price);
    }

    public void delete(Long vehicleId) {
        this.priceRepository.deleteById(vehicleId);
    }
}
