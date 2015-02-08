package com.joinef.eftrains.resource;

import com.joinef.eftrains.api.JourneyRepresentation;
import com.joinef.eftrains.api.RouteRepresentation;
import com.joinef.eftrains.dao.JourneyDao;
import com.joinef.eftrains.entity.Journey;
import com.yammer.metrics.annotation.Timed;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dario.simonetti on 07/02/2015.
 */
@Component
@Path("/journey")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JourneyResource {

    @Autowired
    JourneyDao journeyDao;

    @GET
    @Timed
    public List<RouteRepresentation> findJourney(@QueryParam(value = "from") String from, @QueryParam(value = "to") String to) {
        List<Journey> journeys = journeyDao.findFrom(from, null);
        JourneyRepresentation journeyRepresentation;

        RouteRepresentation.Builder routeRepresentationBuilder = new RouteRepresentation.Builder();

        float totalPrice = 0.0f;
        for (Journey journey : journeys) {
            journeyRepresentation = new JourneyRepresentation.Builder().
                    departureStation(journey.getDepartureStation()).
                    arrivalStation(journey.getArrivalStation()).
                    price(journey.getPrice()).
                    departureTime(journey.getDepartureTime()).
                    arrivalTime(journey.getArrivalTime()).
                    build();
            routeRepresentationBuilder.addJourney(journeyRepresentation);
            totalPrice += journey.getPrice();
        }

        Journey first = journeys.get(0);
        Journey last = journeys.get(journeys.size() - 1);

        routeRepresentationBuilder = routeRepresentationBuilder.
                price(totalPrice).
                duration(calculateDuration(first.getDepartureTime(), last.getArrivalTime())).
                departureStation(first.getDepartureStation()).
                arrivalStation(last.getArrivalStation()).
                departureTime(first.getDepartureTime()).
                arrivalTime(last.getArrivalTime());

        ArrayList<RouteRepresentation> journeyRepresentationList = new ArrayList<>();
        journeyRepresentationList.add(routeRepresentationBuilder.build());
        return journeyRepresentationList;
    }

    private int calculateDuration(DateTime departureTime, DateTime arrivalTime) {
        if (departureTime == null || arrivalTime == null) {
            return 0;
        }

        return Minutes.minutesBetween(arrivalTime, departureTime).getMinutes();
    }

    private DateTime addMinutesFromNow(int minutes) {
        return DateTime.now().plusMinutes(minutes).withSecondOfMinute(0).withMillisOfSecond(0).withZone(DateTimeZone.UTC);
    }
}
