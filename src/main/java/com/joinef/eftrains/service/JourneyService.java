package com.joinef.eftrains.service;

import com.joinef.eftrains.entity.Journey;
import org.joda.time.DateTime;

import java.util.HashMap;

/**
 * Created by dario.simonetti on 08/02/2015.
 */
interface JourneyService {

    public HashMap<String, Journey> findFrom(String startStation, DateTime departureTime);
}
