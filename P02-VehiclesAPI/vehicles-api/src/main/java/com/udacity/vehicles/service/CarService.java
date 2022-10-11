package com.udacity.vehicles.service;

import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import com.udacity.vehicles.exception.CarNotFoundException;
import com.udacity.vehicles.exception.SetVehiclePriceException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private final WebClient maps;
    private final WebClient pricing;
    private final PricingService pricingService;
    private final MapService mapService;

    public CarService(CarRepository repository, WebClient maps, WebClient pricing, PricingService pricingService, MapService mapService) {

        this.repository = repository;
        this.maps = maps;
        this.pricing = pricing;
        this.pricingService = pricingService;
        this.mapService = mapService;
    }

    /**
     * Gathers a list of all vehicles
     *
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     *
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        var selectedCar = this.repository.findById(id);
        if (!selectedCar.isPresent()) {
            throw new CarNotFoundException("Car Not Found");
        }

        var price = this.pricingService.getPrice(id);
        var selectedCarLocation = selectedCar.get().getLocation();
        var location = this.mapService.getAddress(selectedCarLocation);
        selectedCar.get().setLocation(location);
        selectedCar.get().setPrice(price);

        return selectedCar.get();
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     *
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) throws SetVehiclePriceException {
        if (car.getId() != null) {
            this.pricingService.setVehiclePrice(car.getId(), car.getPrice(), "EGP");
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        carToBeUpdated.setPrice(car.getPrice());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }
        var newCar = repository.save(car);
        var price = this.pricingService.setVehiclePrice(newCar.getId(), newCar.getPrice(), "EGP");
        if (price == null)
            throw new SetVehiclePriceException("Set Price Exception");
        return newCar;
    }

    /**
     * Deletes a given car by ID
     *
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {

        var selectedCar = this.repository.findById(id);
        if (!selectedCar.isPresent()) {
            throw new CarNotFoundException("Car Not Found");
        }

        this.pricingService.deleteVehiclePrice(selectedCar.get().getId());
        this.repository.delete(selectedCar.get());
    }
}
