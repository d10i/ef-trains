package com.joinef.eftrains.service;

import com.joinef.eftrains.dao.JourneyDao;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dario.simonetti on 08/02/2015.
 */
@Service
public class JourneyServiceImpl implements JourneyService {
    @Autowired
    private JourneyDao journeyDao;

    public float find(int startStation, int endStation, DateTime departureTime) {
        return journeyDao.find(startStation, endStation, departureTime);
    }

    public int countStations() {
        return journeyDao.countStations();
    }
}
