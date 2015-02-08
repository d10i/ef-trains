package com.joinef.eftrains.resource;

import com.joinef.eftrains.api.JourneyRepresentation;
import com.yammer.metrics.annotation.Timed;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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

    @GET
    @Timed
    public List<List<JourneyRepresentation>> findJourney(@QueryParam(value = "from") int from, @QueryParam(value = "to") int to) {
        JourneyRepresentation journeyRepresentation1 = new JourneyRepresentation.Builder().
                departureStation(from).
                arrivalStation(2).
                price(15.5d).
                departureTime(addMinutesFromNow(10)).
                arrivalTime(addMinutesFromNow(25)).
                build();
        JourneyRepresentation journeyRepresentation2 = new JourneyRepresentation.Builder().
                departureStation(2).
                arrivalStation(to).
                price(20.0d).
                departureTime(addMinutesFromNow(30)).
                arrivalTime(addMinutesFromNow(50)).
                build();

        ArrayList<JourneyRepresentation> optimitedJourneyRepresentation = new ArrayList<JourneyRepresentation>();
        optimitedJourneyRepresentation.add(journeyRepresentation1);
        optimitedJourneyRepresentation.add(journeyRepresentation2);


        ArrayList<List<JourneyRepresentation>> journeyRepresentationList = new ArrayList<List<JourneyRepresentation>>();
        journeyRepresentationList.add(optimitedJourneyRepresentation);

        return journeyRepresentationList;
    }

    private DateTime addMinutesFromNow(int minutes) {
        return DateTime.now().plusMinutes(minutes).withSecondOfMinute(0).withMillisOfSecond(0).withZone(DateTimeZone.UTC);
    }
}
