package com.joinef.eftrains.resource;

import com.joinef.eftrains.api.JourneyRepresentation;
import com.joinef.eftrains.api.RouteRepresentation;
import com.joinef.eftrains.entity.Journey;
import com.joinef.eftrains.service.OptimizationAlgorithm;
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
    OptimizationAlgorithm optimizationAlgorithm;

    @GET
    @Timed
    public List<RouteRepresentation> findJourney(@QueryParam(value = "from") String from, @QueryParam(value = "to") String to) {
        List<List<Journey>> optimizedRoutes = optimizationAlgorithm.performOptimization(from, to);

        ArrayList<RouteRepresentation> optimizedRoutesRepresentation = new ArrayList<>();
        List<JourneyRepresentation> journeyRepresentations;
        for (List<Journey> optimizedRoute : optimizedRoutes) {
            for(int i = 0; i < optimizedRoute.size(); i++) {
                if(optimizedRoute.get(i) == null) {
                    optimizedRoute.remove(i);
                }
            }

            journeyRepresentations = new ArrayList<>();
            for (Journey journey : optimizedRoute) {
                journeyRepresentations.add(journeyToJourneyRepresentation(journey));
            }

            optimizedRoutesRepresentation.add(routeToRouteRepresentation(optimizedRoute, journeyRepresentations));
        }

        return optimizedRoutesRepresentation;
    }

    private RouteRepresentation routeToRouteRepresentation(List<Journey> optimizedRoute, List<JourneyRepresentation> journeyRepresentations) {
        Journey first = optimizedRoute.get(0);
        Journey last = optimizedRoute.get(optimizedRoute.size() - 1);

        int totalPrice = 0;
        for (Journey journey : optimizedRoute) {
            totalPrice += journey.getPrice();
        }

        return new RouteRepresentation.Builder().
                price(totalPrice).
                duration(calculateDuration(first.getDepartureTime(), last.getArrivalTime())).
                departureStation(first.getDepartureStation()).
                arrivalStation(last.getArrivalStation()).
                departureTime(first.getDepartureTime()).
                arrivalTime(last.getArrivalTime()).
                journeys(journeyRepresentations).
                build();
    }

    private int calculateDuration(DateTime departureTime, DateTime arrivalTime) {
        if (departureTime == null || arrivalTime == null) {
            return 0;
        }

        return Minutes.minutesBetween(departureTime, arrivalTime).getMinutes();
    }

    private JourneyRepresentation journeyToJourneyRepresentation(Journey journey) {
        return new JourneyRepresentation.Builder().
                departureStation(journey.getDepartureStation()).
                arrivalStation(journey.getArrivalStation()).
                price(journey.getPrice()).
                departureTime(journey.getDepartureTime()).
                arrivalTime(journey.getArrivalTime()).
                build();
    }

    private DateTime addMinutesFromNow(int minutes) {
        return DateTime.now().plusMinutes(minutes).withSecondOfMinute(0).withMillisOfSecond(0).withZone(DateTimeZone.UTC);
    }
}
