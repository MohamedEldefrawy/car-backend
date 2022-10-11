package com.udacity.vehicles.service;

import com.udacity.vehicles.client.prices.Price;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.exception.SetVehiclePriceException;
import org.springframework.stereotype.Service;

@Service
public class PricingService {
    private final PriceClient priceClient;

    public PricingService(PriceClient priceClient) {
        this.priceClient = priceClient;
    }

    public String getPrice(Long id) {
        return this.priceClient.getPrice(id);
    }

    public Price setVehiclePrice(Long id, String priceValue, String currency) throws SetVehiclePriceException {
        try {
            return this.priceClient.SetVehiclePrice(id, priceValue, currency);
        } catch (SetVehiclePriceException e) {
            throw new SetVehiclePriceException(e.getMessage());
        }
    }

    public void deleteVehiclePrice(Long id) {
        this.priceClient.deleteVehiclePrice(id);
    }
}
