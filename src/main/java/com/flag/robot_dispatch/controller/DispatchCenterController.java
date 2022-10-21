package com.flag.robot_dispatch.controller;

import com.flag.robot_dispatch.model.DispatchCenter;
import com.flag.robot_dispatch.model.Location;
import com.flag.robot_dispatch.service.DispatchCenterService;
import com.flag.robot_dispatch.service.GeoCodingService;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DispatchCenterController {
    private DispatchCenterService dispatchCenterService;

    private GeoCodingService geoCodingService;

    @Autowired
    public DispatchCenterController(DispatchCenterService dispatchCenterService, GeoCodingService geoCodingService) {
        this.dispatchCenterService = dispatchCenterService;
        this.geoCodingService = geoCodingService;
    }

    @PostMapping("/centers")
    public void addCenter(
            @RequestParam("id") Long id,
            @RequestParam("address") String address,
            @RequestParam("name") String name
            )
    {
        Location location = geoCodingService.getLatLng(address);
        DispatchCenter center = new DispatchCenter(id, address, name, location.getGeoPoint().getLon(), location.getGeoPoint().getLat());
        dispatchCenterService.add(center);
    }

}
