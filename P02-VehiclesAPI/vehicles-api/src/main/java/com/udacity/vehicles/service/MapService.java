package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.domain.Location;
import org.springframework.stereotype.Service;

@Service
public class MapService {
    private final MapsClient mapsClient;

    public MapService(MapsClient mapsClient) {
        this.mapsClient = mapsClient;
    }

    public Location getAddress(Location location) {
        return this.mapsClient.getAddress(location);
    }

}
