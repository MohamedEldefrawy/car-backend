package com.udacity.vehicles.service;

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
        RestTemplate restTemplate = new RestTemplate();
        return this.priceClient.getPrice(id);
    }
}
