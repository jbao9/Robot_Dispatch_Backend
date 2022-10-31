package com.flag.robot_dispatch.service;

import com.flag.robot_dispatch.exception.InvalidDeliveryTimeException;
import com.flag.robot_dispatch.model.Vehicle;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TimeEstimateService {
    private static final double TIME_ESTIMATE_RATIO = 2.0;

    public String getTimeEstimate(double deliveryDistance, String expect_pickup_time, Vehicle vehicle) {

        Date date1;
        Date date2;
        double deliveryTimeInHours = (deliveryDistance / vehicle.getType().getSpeed()) * TIME_ESTIMATE_RATIO + 1.0;

        SimpleDateFormat simpleDateFormat
                = new SimpleDateFormat("HH:mm:ss");

        try {
            date1 = simpleDateFormat.parse(expect_pickup_time);
        } catch (ParseException e) {
            throw new InvalidDeliveryTimeException("Invalid Time Input");
        }

        date2 = new Date(date1.getTime() + (long) (deliveryTimeInHours * (60 * 60 * 1000)));

        return simpleDateFormat.format(date2);
    }
}
