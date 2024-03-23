package com.example.yuniavia.Client.ClientHandlerClasses;

import java.io.Serializable;

public class RunwayWithFlightInfo extends Runway implements Serializable {
    private String flight_number;
    private String departure_time;

    public RunwayWithFlightInfo(int flight_id, String state, String picture_path,String number_of_runway,
                                String flight_number, String departure_time) {
        super(flight_id, state, picture_path, number_of_runway);
        this.flight_number = flight_number;
        this.departure_time = departure_time;
    }
    public RunwayWithFlightInfo(int flight_id, String state, String picture_path,String number_of_runway, String occupation,
                                String flight_number, String departure_time) {
        super(flight_id, state, picture_path, number_of_runway, occupation);
        this.flight_number = flight_number;
        this.departure_time = departure_time;
    }

    @Override
    public String toString() {
        return super.toString() +
                "flight_number='" + flight_number + '\'' +
                ", departure_time='" + departure_time + '\'' +
                ", arrival_time='" + arrival_time + '\'' +
                '}';
    }

    public String getFlight_number() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number = flight_number;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    private String arrival_time;



    public RunwayWithFlightInfo() {
        super();
    }


    @Override
    public int getFlight_id() {
        return super.getFlight_id();
    }

    @Override
    public void setFlight_id(int flight_id) {
        super.setFlight_id(flight_id);
    }

    @Override
    public String getState() {
        return super.getState();
    }

    @Override
    public void setState(String state) {
        super.setState(state);
    }

    @Override
    public String getPicture_path() {
        return super.getPicture_path();
    }

    @Override
    public void setPicture_path(String picture_path) {
        super.setPicture_path(picture_path);
    }
}
