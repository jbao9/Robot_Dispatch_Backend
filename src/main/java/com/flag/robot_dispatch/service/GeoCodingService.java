package com.flag.robot_dispatch.service;

import com.flag.robot_dispatch.exception.GeoCodingException;
import com.flag.robot_dispatch.exception.InvalidStayAddressException;
import com.flag.robot_dispatch.model.Location;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GeoCodingService {
    private GeoApiContext context;

    @Autowired
    public GeoCodingService(GeoApiContext context) {
        this.context = context;
    }

    public Location getLatLng(String address) throws GeoCodingException {
        try {
            GeocodingResult result = GeocodingApi.geocode(context, address).await()[0];
            if(result.partialMatch){
                throw new InvalidStayAddressException("Fail to find the address");
            }
            return new Location(new GeoPoint(result.geometry.location.lat, result.geometry.location.lng));
        } catch (IOException | ApiException | InterruptedException e) {
            e.printStackTrace();
            throw new GeoCodingException("Failed to encode address");
        }
    }

}
