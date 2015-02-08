package com.joinef.eftrains.service;

import com.joinef.eftrains.dao.JourneyDao;
import com.joinef.eftrains.dao.StationDao;
import com.joinef.eftrains.entity.Journey;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dario.simonetti on 08/02/2015.
 */
@Service
public class JourneyServiceImpl implements JourneyService {
    @Autowired
    private JourneyDao journeyDao;

    @Autowired
    private StationDao stationDao;

    public JourneyServiceImpl() {
        Initialise();
    }

    private void Initialise() {
        stationsObtained = new HashMap<String, HashMap<String, Journey>>();
    }

    private HashMap<String, HashMap<String, Journey>> stationsObtained;

    public HashMap<String, Journey> findFrom(String departureStation, DateTime departureTime) {
        if (stationsObtained.containsKey(departureStation)) {
            return stationsObtained.get(departureStation);
        }

        List<Journey> journeys = journeyDao.findFrom(departureStation, departureTime);

        HashMap<String, Journey> output = new HashMap<>();
        for (Journey journey : journeys) {
            output.put(journey.getArrivalStation(), journey);
        }

        stationsObtained.put(departureStation, output);

        return output;
    }
}
