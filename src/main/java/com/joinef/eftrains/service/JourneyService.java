package com.joinef.eftrains.service;

import com.joinef.eftrains.entity.Journey;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by dario.simonetti on 08/02/2015.
 */
interface JourneyService {

    public float find(int startStation, int endStation, DateTime departureTime);

    public List<Journey> findFrom(int startStation, DateTime departureTime);

    public int countStations();
}
