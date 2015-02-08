package com.joinef.eftrains.service;

import org.joda.time.DateTime;

/**
 * Created by dario.simonetti on 08/02/2015.
 */
interface JourneyService {

    public float find(int startStation, int endStation, DateTime departureTime);

    public int countStations();
}
