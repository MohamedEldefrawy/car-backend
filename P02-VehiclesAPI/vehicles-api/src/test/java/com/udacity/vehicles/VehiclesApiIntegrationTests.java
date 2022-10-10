package com.udacity.vehicles;

import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VehiclesApiIntegrationTests {


    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int PORT;

    private final String url = "http://localhost:";

    @Test
    public void getVehicleById() {
        ResponseEntity<Car> response =
                this.restTemplate.getForEntity(url + PORT + "/cars/1", Car.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getVehicleByInvalidId() {
        ResponseEntity<Car> response =
                this.restTemplate.getForEntity(url + PORT + "/cars/1231231", Car.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void getAllVehicles() {
        ResponseEntity<Car> response =
                this.restTemplate.getForEntity(url + PORT + "/cars", Car.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void lastDeleteVehicle() {
        var response = this.restTemplate.exchange(url + PORT + "/cars/1", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));
    }

    @Test
    public void firstInsertVehicle() {
        var tempCar = new Car();
        var details = new Details();
        var location = new Location(40.7D, -73.55D);
        details.setBody("newBody");
        details.setEngine("newEngine");
        details.setModel("newModel");
        details.setFuelType("newFuelType");
        details.setNumberOfDoors(4);
        details.setModelYear(2022);
        details.setExternalColor("Black");
        details.setManufacturer(new Manufacturer(101, "Chevrolet"));
        details.setProductionYear(2022);
        details.setMileage(200);

        tempCar.setCondition(Condition.NEW);
        tempCar.setCreatedAt(LocalDateTime.now());
        tempCar.setModifiedAt(LocalDateTime.now());
        tempCar.setDetails(details);
        tempCar.setLocation(location);
        var httpEntity = new HttpEntity<>(tempCar);
        var response = this.restTemplate.exchange(url + PORT + "/cars", HttpMethod.POST, httpEntity, Car.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @Test
    public void updateVehicle() {
        var tempCar = new Car();
        var details = new Details();
        var location = new Location(40.7D, -73.55D);
        details.setBody("newBody");
        details.setEngine("newEngine");
        details.setModel("newModel");
        details.setFuelType("newFuelType");
        details.setNumberOfDoors(4);
        details.setModelYear(2022);
        details.setExternalColor("Black");
        details.setManufacturer(new Manufacturer(101, "Chevrolet"));
        details.setProductionYear(2022);
        details.setMileage(200);

        tempCar.setCondition(Condition.NEW);
        tempCar.setCreatedAt(LocalDateTime.now());
        tempCar.setModifiedAt(LocalDateTime.now());
        tempCar.setDetails(details);
        tempCar.setLocation(location);
        tempCar.setPrice("252525252");
        tempCar.setId(1L);
        var httpEntity = new HttpEntity<>(tempCar);
        var response = this.restTemplate.exchange(url + PORT + "/cars/1", HttpMethod.PUT, httpEntity, Car.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

}


