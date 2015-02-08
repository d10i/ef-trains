package com.joinef.eftrains.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joinef.eftrains.commons.serialization.DetailDateTimeDeserializer;
import com.joinef.eftrains.commons.serialization.DetailDateTimeSerializer;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class RouteRepresentation {

    @JsonProperty
    @NotNull
    private int price;

    @JsonProperty
    private Integer duration;

    @JsonProperty
    @NotNull
    private String departureStation;

    @JsonProperty
    @NotNull
    private String departureStationName;

    @JsonProperty
    @NotNull
    private String arrivalStation;

    @JsonProperty
    @NotNull
    private String arrivalStationName;

    @JsonProperty
    @JsonDeserialize(using = DetailDateTimeDeserializer.class)
    @JsonSerialize(using = DetailDateTimeSerializer.class)
    private DateTime departureTime;

    @JsonProperty
    @JsonDeserialize(using = DetailDateTimeDeserializer.class)
    @JsonSerialize(using = DetailDateTimeSerializer.class)
    private DateTime arrivalTime;

    @JsonProperty
    List<JourneyRepresentation> journeys;

    public RouteRepresentation(Builder routeRepresentationBuilder) {
        this.price = routeRepresentationBuilder.price;
        this.duration = routeRepresentationBuilder.duration;
        this.departureStation = routeRepresentationBuilder.departureStation;
        this.departureStationName = routeRepresentationBuilder.departureStationName;
        this.arrivalStation = routeRepresentationBuilder.arrivalStation;
        this.arrivalStationName = routeRepresentationBuilder.arrivalStationName;
        this.departureTime = routeRepresentationBuilder.departureTime;
        this.arrivalTime = routeRepresentationBuilder.arrivalTime;
        this.journeys = routeRepresentationBuilder.journeys;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getDepartureStationName() {
        return departureStationName;
    }

    public void setDepartureStationName(String departureStationName) {
        this.departureStationName = departureStationName;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public String getArrivalStationName() {
        return arrivalStationName;
    }

    public void setArrivalStationName(String arrivalStationName) {
        this.arrivalStationName = arrivalStationName;
    }

    public DateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(DateTime departureTime) {
        this.departureTime = departureTime;
    }

    public DateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(DateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public List<JourneyRepresentation> getJourneys() {
        return journeys;
    }

    public void setJourneys(List<JourneyRepresentation> journeys) {
        this.journeys = journeys;
    }

    public static class Builder {
        private int price;
        private Integer duration;
        private String departureStation;
        private String departureStationName;
        private String arrivalStation;
        private String arrivalStationName;
        private DateTime departureTime;
        private DateTime arrivalTime;
        private List<JourneyRepresentation> journeys = new ArrayList<>();

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder duration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public Builder departureStation(String departureStation) {
            this.departureStation = departureStation;
            return this;
        }

        public Builder departureStationName(String departureStationName) {
            this.departureStationName = departureStationName;
            return this;
        }

        public Builder arrivalStation(String arrivalStation) {
            this.arrivalStation = arrivalStation;
            return this;
        }

        public Builder arrivalStationName(String arrivalStationName) {
            this.arrivalStationName = arrivalStationName;
            return this;
        }

        public Builder departureTime(DateTime departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public Builder arrivalTime(DateTime arrivalTime) {
            this.arrivalTime = arrivalTime;
            return this;
        }

        public Builder addJourney(JourneyRepresentation journey) {
            this.journeys.add(journey);
            return this;
        }

        public Builder journeys(List<JourneyRepresentation> journeyRepresentations) {
            this.journeys = journeyRepresentations;
            return this;
        }

        public RouteRepresentation build() {
            return new RouteRepresentation(this);
        }
    }
}
