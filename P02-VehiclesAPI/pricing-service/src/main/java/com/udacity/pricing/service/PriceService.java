package com.udacity.pricing.service;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.exception.InsertPriceToVehicleException;
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

    public Price insert(Price price) throws InsertPriceToVehicleException {
        try {
            var selectedPRiceOptional = this.priceRepository.findById(price.getVehicleId());
            if (!selectedPRiceOptional.isPresent())
                return this.priceRepository.save(price);
            else throw new InsertPriceToVehicleException("Price is already set");
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new InsertPriceToVehicleException(illegalArgumentException.getMessage());
        }
    }

    public Price update(Price price) throws PriceNotFoundException {
        try {
            var selectedPRiceOptional = this.priceRepository.findById(price.getVehicleId());
            if (!selectedPRiceOptional.isPresent())
                throw new PriceNotFoundException("Vehicle Price Not Found");
            else
                return this.priceRepository.save(price);

        } catch (IllegalArgumentException e) {
            throw new PriceNotFoundException("Vehicle Price Not Found");
        }
    }

    public void delete(Long vehicleId) throws PriceNotFoundException {
        var selectedPrice = this.priceRepository.findById(vehicleId);
        if (selectedPrice.isPresent())
            this.priceRepository.deleteById(vehicleId);
        else
            throw new PriceNotFoundException("Price of Vehicle Not Found");
    }
}
