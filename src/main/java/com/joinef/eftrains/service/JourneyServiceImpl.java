package com.joinef.eftrains.service;

import com.joinef.eftrains.dao.JourneyDao;
import com.joinef.eftrains.entity.Journey;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        weights = new float[stationCount][];
        for (int i = 0; i < stationCount; i++) {
            weights[i] = new float[stationCount];
            for (int j = 0; j < stationCount; j++) {
                weights[i][j] = Float.POSITIVE_INFINITY;
            }
        }
    }

    private float[][] weights;

    private int stationCount;

    public float find(int startStation, int endStation, DateTime departureTime) {

        if (Float.isInfinite(weights[startStation][endStation])) {
            weights[startStation][endStation] = journeyDao.find(startStation, endStation, departureTime);
        }

        return weights[startStation][endStation];
    }

    public List<Journey> findFrom(int departureStation, DateTime departureTime) {
        List<Journey> journeys = new ArrayList<Journey>();

        for (int i = 0; i < stationCount; i++) {
            if (i != departureStation) {
                float weight = find(departureStation, i, departureTime);

                if (!Float.isNaN(weight)) {
                    journeys.add(new Journey(weight, departureStation, i, departureTime, null));
                }
            }
        }

        return journeys;
    }

    public int countStations() {
        return stationCount;
    }
}
