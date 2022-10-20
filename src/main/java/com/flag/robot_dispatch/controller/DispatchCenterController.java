package com.flag.robot_dispatch.controller;

import com.flag.robot_dispatch.model.DispatchCenter;
import com.flag.robot_dispatch.service.DispatchCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DispatchCenterController {
    private DispatchCenterService dispatchCenterService;

    @Autowired
    public DispatchCenterController(DispatchCenterService dispatchCenterService) {
        this.dispatchCenterService = dispatchCenterService;
    }

    @PostMapping("/centers")
    public void addCenter(
            @RequestParam("id") String id,
            @RequestParam("address") String address,
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon)
    {
        DispatchCenter center = new DispatchCenter(id, address, lat, lon);
        dispatchCenterService.add(center);
    }

}
