package com.joinef.eftrains.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joinef.eftrains.commons.serialization.DetailDateTimeDeserializer;
import com.joinef.eftrains.commons.serialization.DetailDateTimeSerializer;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

public class JourneyRepresentation {

    @JsonProperty
    @NotNull
    private String departureStation;

    @JsonProperty
    @NotNull
    private String arrivalStation;

    @JsonProperty
    private int price;

    @JsonProperty
    @JsonDeserialize(using = DetailDateTimeDeserializer.class)
    @JsonSerialize(using = DetailDateTimeSerializer.class)
    private DateTime departureTime;

    @JsonProperty
    @JsonDeserialize(using = DetailDateTimeDeserializer.class)
    @JsonSerialize(using = DetailDateTimeSerializer.class)
    private DateTime arrivalTime;

    public JourneyRepresentation(Builder journeyRepresentationBuilder) {
        this.departureStation = journeyRepresentationBuilder.departureStation;
        this.arrivalStation = journeyRepresentationBuilder.arrivalStation;
        this.price = journeyRepresentationBuilder.price;
        this.departureTime = journeyRepresentationBuilder.departureTime;
        this.arrivalTime = journeyRepresentationBuilder.arrivalTime;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    @Override
    public String toString() {
        return "JourneyRepresentation{" +
                "price=" + price +
                ", departureStation=" + departureStation +
                ", arrivalStation=" + arrivalStation +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                '}';
    }

    public static class Builder {
        private String departureStation;
        private String arrivalStation;
        private int price;
        private DateTime departureTime;
        private DateTime arrivalTime;

        public Builder departureStation(String departureStation) {
            this.departureStation = departureStation;
            return this;
        }

        public Builder arrivalStation(String arrivalStation) {
            this.arrivalStation = arrivalStation;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
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

        public JourneyRepresentation build() {
            return new JourneyRepresentation(this);
        }
    }
}
