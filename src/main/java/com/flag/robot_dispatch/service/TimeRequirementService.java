package com.flag.robot_dispatch.service;

import com.flag.robot_dispatch.exception.InvalidDeliveryTimeException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TimeRequirementService {

    public double getTimeRequirement(String pickupTime, String deliveryTime) {

        Date date1;
        Date date2;
        double differenceInHours;

        SimpleDateFormat simpleDateFormat
                = new SimpleDateFormat("HH:mm:ss");
        try {
            date1 = simpleDateFormat.parse(pickupTime);
            date2 = simpleDateFormat.parse(deliveryTime);
        } catch (ParseException e) {
            throw new InvalidDeliveryTimeException("Invalid Time Input");
        }

        double differenceInMilliSeconds
                = Math.abs(date2.getTime() - date1.getTime());

        differenceInHours
                = (differenceInMilliSeconds / (60 * 60 * 1000))
                % 24;

        return differenceInHours;
    }
}
