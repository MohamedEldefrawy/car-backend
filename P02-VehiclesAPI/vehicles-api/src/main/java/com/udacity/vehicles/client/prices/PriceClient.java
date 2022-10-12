package com.udacity.vehicles.client.prices;

import com.udacity.vehicles.exception.SetVehiclePriceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

/**
 * Implements a class to interface with the Pricing Client for price data.
 */
@Component
public class PriceClient {

    private static final Logger log = LoggerFactory.getLogger(PriceClient.class);

    private final WebClient client;

    public PriceClient(WebClient pricing) {
        this.client = pricing;
    }

    /**
     * Gets a vehicle price from the pricing client, given vehicle ID.
     *
     * @param vehicleId ID number of the vehicle for which to get the price
     * @return Currency and price of the requested vehicle,
     * error message that the vehicle ID is invalid, or note that the
     * service is down.
     */
    public String getPrice(Long vehicleId) {
        try {
            var price = client
                    .get()
                    .uri(new URI("http://localhost:8082/prices/" + vehicleId))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve().bodyToMono(Price.class).block();

            if (price != null)
                return String.format("%s %s", price.getCurrency(), price.getPrice());

            return null;
        } catch (Exception e) {
            log.error("Unexpected error retrieving price for vehicle {}", vehicleId, e);
        }
        return "(consult price)";
    }

    public Price SetVehiclePrice(Price price) throws SetVehiclePriceException {
        try {

            var payload = "{\n" +
                    "\"vehicleId\":" + "\"" + price.getVehicleId().toString() + "\"" + ",\n" +
                    "\"currency\":" + "\"" + price.getCurrency() + "\"" + ",\n" +
                    "\"price\":" + "\"" + price.getPrice() + "\"" + "\n" +
                    "}";

            return client.post()
                    .uri(new URI("http://localhost:8082/prices"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromObject(payload))
                    .retrieve().bodyToMono(Price.class).block();
        } catch (Exception e) {
            throw new SetVehiclePriceException("vehicle price set exception");
        }
    }

    public void deleteVehiclePrice(Long vehicleId) {
        try {
            client
                    .delete()
                    .uri(new URI("http://localhost:8082/prices/" + vehicleId))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve().bodyToMono(Void.class).block();
        } catch (Exception e) {
            log.error("Unexpected error retrieving price for vehicle {}", vehicleId, e);
        }
    }

    public Price updateVehiclePrice(Price price) throws SetVehiclePriceException {
        try {
            var payload = "{\n" +
                    "\"vehicleId\":" + "\"" + price.getVehicleId().toString() + "\"" + ",\n" +
                    "\"currency\":" + "\"" + price.getCurrency() + "\"" + ",\n" +
                    "\"price\":" + "\"" + price.getPrice() + "\"" + "\n" +
                    "}";

            return client.put()
                    .uri(new URI("http://localhost:8082/prices"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromObject(payload))
                    .retrieve().bodyToMono(Price.class).block();
        } catch (
                Exception e) {
            throw new SetVehiclePriceException("vehicle price set exception");
        }
    }
}
