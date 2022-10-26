package com.flag.robot_dispatch.controller;

import com.flag.robot_dispatch.model.DispatchCenter;
import com.flag.robot_dispatch.model.Location;
import com.flag.robot_dispatch.service.DispatchCenterService;
import com.flag.robot_dispatch.service.GeoCodingService;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    ) {
//        System.out.println(address);
        Location location = geoCodingService.getLatLng(address);
//        System.out.println(location.getGeoPoint().getLat());
        DispatchCenter center = new DispatchCenter.Builder()
                .setId(id)
                .setName(name)
                .setAddress(address)
                .setLon(location.getGeoPoint().getLon())
                .setLat(location.getGeoPoint().getLat()).
                build();
        dispatchCenterService.add(center);
    }

    @GetMapping(value = "/centers/{id}")
    public DispatchCenter getCenter(@PathVariable Long id) {
        return dispatchCenterService.getCenterById(id);
    }

    @DeleteMapping(value = "/centers/{id}")
    public void deleteCenter(@PathVariable Long id) {
        dispatchCenterService.deleteCenter(id);
    }

}
