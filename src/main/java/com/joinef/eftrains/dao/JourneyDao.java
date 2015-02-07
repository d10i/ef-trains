package com.joinef.eftrains.dao;

import org.joda.time.DateTime;

/**
 * Created by Jamie on 07/02/2015.
 */
public interface JourneyDao {

    float find(int startStation, int endStation, DateTime departureTime);

    int countStations();
}
