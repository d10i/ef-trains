package com.joinef.eftrains.service;

import ch.qos.logback.classic.joran.JoranConfigurator;
import com.joinef.eftrains.dao.JourneyDao;
import com.joinef.eftrains.entity.Journey;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by dario.simonetti on 08/02/2015.
 */
@Service
public class JourneyServiceImpl implements JourneyService {
    @Autowired
    private JourneyDao journeyDao;

    public JourneyServiceImpl() {
        Initialise();
    }

    private void Initialise() {
        stationCount = journeyDao.countStations();
        stationsObtained = new Hashtable<Integer, Hashtable<Integer, Journey>>();
    }

    private int stationCount;

    private Hashtable<Integer, Hashtable<Integer, Journey>> stationsObtained;

    public Hashtable<Integer, Journey> findFrom(int departureStation, DateTime departureTime)
    {
        if(stationsObtained.containsKey(departureStation))
        {
            return stationsObtained.get(departureStation);
        }

        List<Journey> journeys = journeyDao.findFrom(departureStation, departureTime);

        Hashtable<Integer, Journey> output = new Hashtable<Integer, Journey>();
        for(int i =0; i < journeys.size(); i++)
        {
            Journey journey = journeys.get(i);
            output.put(journey.getArrivalStation(), journey);
        }

        stationsObtained.put(departureStation, output);

        return output;
    }

    public int countStations() {
        return stationCount;
    }
}
