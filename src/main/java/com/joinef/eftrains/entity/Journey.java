package com.joinef.eftrains.entity;

import org.joda.time.DateTime;

/**
 * Created by Jamie on 07/02/2015.
 */
public class Journey {

    public Journey(double price, int departureStation, int arrivalStation, DateTime departureTime, DateTime arrivalTime) {
        this.price = price;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Journey(double price, int departureStation, int arrivalStation) {
        this.price = price;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
    }

    private double price;

    private int departureStation;

    private int arrivalStation;

    private DateTime departureTime;

    private DateTime arrivalTime;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    @java.lang.Override
    public java.lang.String toString() {
        return "Journey{" +
                "price=" + price +
                ", departureStation=" + departureStation +
                ", arrivalStation=" + arrivalStation +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                '}';
    }
}
