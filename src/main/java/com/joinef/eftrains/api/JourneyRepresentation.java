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
    private int departureStation;

    @JsonProperty
    @NotNull
    private int arrivalStation;

    @JsonProperty
    private double price;

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

    public int getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(int departureStation) {
        this.departureStation = departureStation;
    }

    public int getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(int arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
        private int departureStation;
        private int arrivalStation;
        private double price;
        private DateTime departureTime;
        private DateTime arrivalTime;

        public Builder departureStation(int departureStation) {
            this.departureStation = departureStation;
            return this;
        }

        public Builder arrivalStation(int arrivalStation) {
            this.arrivalStation = arrivalStation;
            return this;
        }

        public Builder price(double price) {
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
