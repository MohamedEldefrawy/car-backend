package com.udacity.boogle.maps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maps")
public class MapsController {

    @GetMapping
    public Address get(@RequestParam("lat") Double lat, @RequestParam("lon") Double lon) {
        return MockAddressRepository.getRandom();
    }
}
