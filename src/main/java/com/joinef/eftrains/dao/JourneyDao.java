package com.joinef.eftrains.dao;

import com.joinef.eftrains.entity.Journey;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Jamie on 07/02/2015.
 */
@Repository
public interface JourneyDao {

    List<Journey> findFrom(String departureStation, DateTime departureTime);
}
