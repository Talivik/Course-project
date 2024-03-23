package com.example.yuniavia.Client.ClientHandlerClasses;

import java.io.Serializable;

public class Flight implements Serializable {
    private int flight_id;
    private String departure_city;
    private String arrival_city;
    private String departure_time;
    private String duration;
    private String arrival_time;
    private String note;
    private String flight_number;


    public Flight(int flight_id,String departure_city, String arrival_city, String departure_time, String duration, String arrival_time, String note,
                  String flight_number) {
        this.flight_id=flight_id;
        this.departure_city = departure_city;
        this.arrival_city = arrival_city;
        this.departure_time = departure_time;
        this.duration = duration;
        this.arrival_time = arrival_time;
        this.note = note;
        this.flight_number=flight_number;
    }

    public Flight() {

    }

    @Override
    public String toString() {
        return "Flight{" +
                "flight_id='" + flight_id + '\'' +
                "departure_city='" + departure_city + '\'' +
                ", arrival_city='" + arrival_city + '\'' +
                ", departure_time='" + departure_time + '\'' +
                ", duration='" + duration + '\'' +
                ", arrival_time='" + arrival_time + '\'' +
                ", note='" + note + '\'' +
                ", flight_number='" + flight_number + '\'' +
                '}';
    }
    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public String getDeparture_city() {
        return departure_city;
    }

    public void setDeparture_city(String departure_city) {
        this.departure_city = departure_city;
    }

    public String getArrival_city() {
        return arrival_city;
    }

    public void setArrival_city(String arrival_city) {
        this.arrival_city = arrival_city;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }
}
