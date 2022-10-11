package com.udacity.vehicles.service;

import com.udacity.vehicles.client.prices.Price;
import com.udacity.vehicles.client.prices.PriceClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PricingService {
    private final PriceClient priceClient;

    public PricingService(PriceClient priceClient) {
        this.priceClient = priceClient;
    }

    public String getPrice(Long id) {
        return this.priceClient.getPrice(id);
    }

    public Price setVehiclePrice(Long id, String priceValue, String currency) {
        try {
            return this.priceClient.SetVehiclePrice(id, priceValue, currency);
        } catch (SetVehiclePriceException e) {
            throw new CarNotFoundException(e.getMessage());
        }
    }

    public void deleteVehiclePrice(Long id) {
        this.priceClient.deleteVehiclePrice(id);
    }
}
